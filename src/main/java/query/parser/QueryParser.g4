parser grammar QueryParser;

@header {
    package query.parser;
    import java.math.BigDecimal;
    import query.backtracking.*;
    import query.search.*;
    import executor.*;
    import java.util.*;
}

@members {
    private ExecutionContext exeCtx;

    public QueryParser(TokenStream input, ExecutionContext extCtx) {
        this(input);
        this.exeCtx = extCtx;
    }

    private ConstraintExpression combine(ConstraintExpression i, ConstraintExpression e) {
        if (i == null && e == null) return null;
        else if (i == null) return new NotExpression(e);
        else if (e == null) return i;
        else return new AndExpression(i, new NotExpression(e));
    }
}

options {
	tokenVocab = QueryLexer;
}

start returns [LinkedList<QueryStatement> stmts = new LinkedList<>()]
    : (connectStmt | assignStmt | searchStmt | displayStmt | exportStmt)*
    ;

connectStmt : CONNECTDB STRING SEMICOLON {
    String str = $STRING.text;
    $start::stmts.add(new ConnectDbStatement(exeCtx, str.substring(1, str.length() - 1)));
};

assignStmt : ID EQ graphExpr SEMICOLON {
    $start::stmts.add(new AssignStatement(exeCtx, $ID.text, $graphExpr.gq));
    }
    | ID EQ searchExpr SEMICOLON {
        $start::stmts.add(new SearchStatement(exeCtx, $ID.text, $searchExpr.sc));
    }
    ;

searchExpr returns [SearchConstraints sc]
    : SEARCH FROM dataSource nodeMatchTerm relatTerm {
        $sc = new SearchConstraints($dataSource.source);
        $sc.setNodeConstraints($nodeMatchTerm.cm);
        $sc.setEdges($relatTerm.relations);
        $sc.setEdgeConstraints($relatTerm.opts);
    }
    ;

searchStmt locals [SearchConstraints sc]
    : SEARCH FROM dataSource nodeMatchTerm relatTerm returnTerm SEMICOLON {
        $sc = new SearchConstraints($dataSource.source);
        $sc.setNodeConstraints($nodeMatchTerm.cm);
        $sc.setEdges($relatTerm.relations);
        $sc.setEdgeConstraints($relatTerm.opts);
        $sc.setReturnName($returnTerm.name, $returnTerm.isDisplay);
        $start::stmts.add(new SearchStatement(exeCtx, $sc));
    }
    ;

displayStmt : DISPLAY graphExpr SEMICOLON {
    $start::stmts.add(new DisplayStatement(exeCtx, $graphExpr.gq));
} ;

exportStmt : EXPORT graphExpr AS STRING SEMICOLON {
    String str = $STRING.text;
    $start::stmts.add(new ExportStatement(exeCtx, $graphExpr.gq, str.substring(1, str.length() - 1)));
} ;

nodeMatchTerm returns [HashMap<String, ConstraintExpression> cm = new HashMap<String, ConstraintExpression>()]
    : WHERE nodeAttributes
    ;

relatTerm returns [ArrayList<ArrayList<String>> relations = new ArrayList<ArrayList<String>>(),
                   ArrayList<List<String>> opts = new ArrayList<List<String>>()]
    : WITH relatConst
    |
    ;

returnTerm returns [String name, Boolean isDisplay]
    : RETURN ASTERISK  {
        $name = "deprecated!";
        $isDisplay = true;
    }
    | RETURN ASTERISK AS ID {
        $name = $ID.text;
        $isDisplay = false;
    }
    ;

nodeAttributes returns [String name, ConstraintExpression ce]
    : na1=nodeAttributes COMMA na2=nodeAttributes
    | ID LEFTBRACE expr RIGHTBRACE {
        $nodeMatchTerm::cm.put($ID.text, $expr.e);
    }
    ;

logicOpt returns [String opt, String time, String unit]
    : LOGAND LEFTSQUARE LT INT SECOND RIGHTSQUARE {$opt="and"; $time=$INT.text; $unit=$SECOND.text;}
    | LOGAND LEFTSQUARE LT INT MINUTE RIGHTSQUARE {$opt="and"; $time=$INT.text; $unit=$MINUTE.text;}
    | LOGAND LEFTSQUARE LT INT MS RIGHTSQUARE {$opt="and"; $time=$INT.text; $unit=$MS.text;}
    | LOGOR {$opt="or"; $time="0"; $unit="null";}
    ;

relatConst
    : relatConst logicOpt relatConst {
        List<String> al = Arrays.asList($logicOpt.opt, $logicOpt.time, $logicOpt.unit);
        $relatTerm::opts.add(al);
    }
    | relatExpr {
        $relatTerm::relations.add($relatExpr.relation);
    }
    ;

relatExpr returns [ArrayList<String> relation]
    : id1=ID ARROW id2=ID {$relation = new ArrayList<>(Arrays.asList($id1.text, "null", $id2.text));}
    | id1=ID (LEFTSQUARE optType RIGHTSQUARE)? ARROW id2=ID
        {$relation = new ArrayList<>(Arrays.asList($id1.text, $optType.text, $id2.text));}
    ;

dataSource returns [String source]
    : DB LEFTBRACKET ID RIGHTBRACKET
    {
        $start::stmts.add(new ConnectDbStatement(exeCtx, $ID.text));
        $source = null;
    }
    | ID
    {
        $source = $ID.text;
    }
    ;

graphExpr returns [GraphQuery gq]
    : LEFTBRACKET graphExpr RIGHTBRACKET { $gq = $graphExpr.gq; }
    | ID { $gq = new VariableGraphQuery(exeCtx, $ID.text); }
    | l=graphExpr UNION r=graphExpr { $gq = new UnionGraphQuery($l.gq, $r.gq); }
    | l=graphExpr INTERSECTION r=graphExpr { $gq = new IntersectionGraphQuery($l.gq, $r.gq); }
    | l=graphExpr DIFFERENCE r=graphExpr { $gq = new DifferenceGraphQuery($l.gq, $r.gq); }
    | trackExpr { $gq = $trackExpr.gq; }
    ;

trackExpr // and selectExpr
    returns [GraphQuery gq]
    locals [BackTrackConstraints bc, BackTrack bt]
    : direction TRACK startTerm FROM dataSource
    {
        if ($startTerm.graphID != null) {
            // The POI is a graph
            $bc = new BackTrackConstraints($startTerm.graphID);
        } else {
            $bc = new BackTrackConstraints($startTerm.ce);
        }
        $bc.setIgnoreConstraints(exeCtx.isIgnoreConstraints());
    }
      filterTerm limitTerm
    {
        if ($dataSource.source == null) {
            // The data source is a local graph
            $bt = exeCtx.isUseRecursive() ? new BackTrackRemoteRecursive() : new BackTrackRemote();
        } else {
            $bt = new BackTrackLocal(exeCtx, $dataSource.source);
        }
        $gq = new TrackGraphQuery(exeCtx, $direction.isBack, $bt, $bc);
    }
    | SEARCH FROM graphExpr { $bc = new BackTrackConstraints(); }
      filterTerm { $gq = new SelectGraphQuery($graphExpr.gq, $bc); }
    ;

direction returns [boolean isBack]
    : BACK { $isBack = true; }
    | FORWARD { $isBack = false; }
    ;

startTerm returns [ConstraintExpression ce, String graphID]
    : ID { $graphID = $ID.text; }
    | constraintExpr { $ce = $constraintExpr.e; }
    ;

filterTerm : INCLUDE i=typeFilterTerm {
               $trackExpr::bc.setNodeConstraints($i.n);
               $trackExpr::bc.setEdgeConstraints($i.e);
           }
           | EXCLUDE e=typeFilterTerm {
               if ($e.n != null) $trackExpr::bc.setNodeConstraints(new NotExpression($e.n));
               if ($e.e != null) $trackExpr::bc.setEdgeConstraints(new NotExpression($e.e));
           }
           | INCLUDE i=typeFilterTerm EXCLUDE e=typeFilterTerm {
               $trackExpr::bc.setNodeConstraints(combine($i.n, $e.n));
               $trackExpr::bc.setEdgeConstraints(combine($i.e, $e.e));
           }
           | EXCLUDE e=typeFilterTerm INCLUDE i=typeFilterTerm {
               $trackExpr::bc.setNodeConstraints(combine($i.n, $e.n));
               $trackExpr::bc.setEdgeConstraints(combine($i.e, $e.e));
           }
           |
           ;

typeFilterTerm returns [ConstraintExpression n, ConstraintExpression e]
    : NODES ne=constraintExpr { $n = $ne.e; }
    | EDGES ee=constraintExpr { $e = $ee.e; }
    | NODES ne=constraintExpr COMMA EDGES ee=constraintExpr
      { $n = $ne.e; $e = $ee.e; }
    | EDGES ee=constraintExpr COMMA NODES ne=constraintExpr
      { $n = $ne.e; $e = $ee.e; }
    ;


limitTerm : LIMIT STEP s=INT { $trackExpr::bc.setStepConstraint($s.int); }
          | LIMIT TIME t=INT { $trackExpr::bc.setTimeConstraintSecs($t.int); }
          | LIMIT STEP s=INT COMMA TIME t=INT {
              $trackExpr::bc.setStepConstraint($s.int);
              $trackExpr::bc.setTimeConstraintSecs($t.int);
          }
          | LIMIT TIME t=INT COMMA STEP s=INT {
              $trackExpr::bc.setStepConstraint($s.int);
              $trackExpr::bc.setTimeConstraintSecs($t.int);
          }
          |
          ;

constraintExpr returns [ConstraintExpression e] : WHERE expr { $e = $expr.e; } ;

expr returns [ConstraintExpression e]
    : LEFTBRACKET expr RIGHTBRACKET { $e = $expr.e; }
    | NOT e1=expr { $e = new NotExpression($e1.e); }
    | e1=expr AND e2=expr { $e = new AndExpression($e1.e, $e2.e); }
    | e1=expr OR e2=expr { $e = new OrExpression($e1.e, $e2.e); }
    | e1=expr COMMA e2=expr { $e = new AndExpression($e1.e, $e2.e); }
    | binaryExpr { $e = $binaryExpr.b; }
    ;

binaryExpr returns [BinaryExpression b]
    : strId eqOp entityType {
              $b = new BinaryExpression($strId.text, $eqOp.v, $entityType.text);
    }
    | strId eqOp STRING {
        String str = $STRING.text;
        str = str.substring(1, str.length() - 1);
        boolean isLike = $eqOp.v == BinaryOperator.Like;
        $b = new BinaryExpression($strId.text, $eqOp.v, isLike ? "%" + str + "%" : str);
    }
    | numId numOp INT { $b = new BinaryExpression($numId.text, $numOp.v, new BigDecimal($INT.text)); }
    ;

strId : TYPE | NAME | PATH | DSTIP | SRCIP | EXENAME | EXEPATH
      | CMDLINE | OPTYPE
      ;

numId : IDSTR | SRCID | DSTID | STARTTIME | ENDTIME | AMOUNT | PID
      | DSTPORT | SRCPORT ;

entityType : PROCESS | FILE | NETWORK;

optType : READ | WRITE | EXECVE;

eqOp returns [BinaryOperator v]
    : EQ { $v = BinaryOperator.Equal; }
    | NEQ { $v = BinaryOperator.NotEqual; }
    | LIKE { $v = BinaryOperator.Like; }
    ;

numOp returns [BinaryOperator v]
    : eqOp { $v = $eqOp.v; }
    | GT { $v = BinaryOperator.MoreThan; }
    | GEQ { $v = BinaryOperator.MoreEqual; }
    | LT { $v = BinaryOperator.LessThan; }
    | LEQ { $v = BinaryOperator.LessEqual; }
    ;

// numerical returns [double val]
//     : INT { $val = $INT.int; }
//     | FLOAT { $val = Double.parseDouble($FLOAT.text); }
//     ;
