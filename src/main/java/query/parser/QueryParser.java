// Generated from QueryParser.g4 by ANTLR 4.9.2

    package query.parser;
    import java.math.BigDecimal;
    import query.backtracking.*;
    import query.search.*;
    import executor.*;
    import java.util.*;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class QueryParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		SEMICOLON=1, COMMA=2, ASTERISK=3, LEFTBRACKET=4, RIGHTBRACKET=5, LEFTBRACE=6, 
		RIGHTBRACE=7, LEFTSQUARE=8, RIGHTSQUARE=9, AND=10, OR=11, NOT=12, IN=13, 
		FROM=14, WITH=15, BACK=16, FORWARD=17, TRACK=18, SEARCH=19, AS=20, DISPLAY=21, 
		EXPORT=22, RETURN=23, SELECT=24, CONNECTDB=25, DB=26, WHERE=27, INCLUDE=28, 
		EXCLUDE=29, NODES=30, EDGES=31, LIMIT=32, TIME=33, STEP=34, MS=35, SECOND=36, 
		MINUTE=37, TYPE=38, NAME=39, PATH=40, DSTIP=41, DSTPORT=42, SRCIP=43, 
		SRCPORT=44, PID=45, EXENAME=46, EXEPATH=47, CMDLINE=48, OPTYPE=49, IDSTR=50, 
		SRCID=51, DSTID=52, STARTTIME=53, ENDTIME=54, AMOUNT=55, PROCESS=56, FILE=57, 
		NETWORK=58, READ=59, WRITE=60, EXECVE=61, NULL=62, ARROW=63, EQ=64, NEQ=65, 
		GT=66, GEQ=67, LT=68, LEQ=69, LIKE=70, UNION=71, INTERSECTION=72, DIFFERENCE=73, 
		LOGAND=74, LOGOR=75, INT=76, STRING=77, ID=78, NEWLINE=79, WS=80, COMMENT=81, 
		LINE_COMMENT=82;
	public static final int
		RULE_start = 0, RULE_connectStmt = 1, RULE_assignStmt = 2, RULE_searchExpr = 3, 
		RULE_searchStmt = 4, RULE_displayStmt = 5, RULE_exportStmt = 6, RULE_nodeMatchTerm = 7, 
		RULE_relatTerm = 8, RULE_returnTerm = 9, RULE_nodeAttributes = 10, RULE_logicOpt = 11, 
		RULE_relatConst = 12, RULE_relatExpr = 13, RULE_dataSource = 14, RULE_graphExpr = 15, 
		RULE_trackExpr = 16, RULE_direction = 17, RULE_startTerm = 18, RULE_filterTerm = 19, 
		RULE_typeFilterTerm = 20, RULE_limitTerm = 21, RULE_constraintExpr = 22, 
		RULE_expr = 23, RULE_binaryExpr = 24, RULE_strId = 25, RULE_numId = 26, 
		RULE_entityType = 27, RULE_optType = 28, RULE_eqOp = 29, RULE_numOp = 30;
	private static String[] makeRuleNames() {
		return new String[] {
			"start", "connectStmt", "assignStmt", "searchExpr", "searchStmt", "displayStmt", 
			"exportStmt", "nodeMatchTerm", "relatTerm", "returnTerm", "nodeAttributes", 
			"logicOpt", "relatConst", "relatExpr", "dataSource", "graphExpr", "trackExpr", 
			"direction", "startTerm", "filterTerm", "typeFilterTerm", "limitTerm", 
			"constraintExpr", "expr", "binaryExpr", "strId", "numId", "entityType", 
			"optType", "eqOp", "numOp"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "';'", "','", "'*'", "'('", "')'", "'{'", "'}'", "'['", "']'", 
			"'and'", "'or'", "'not'", "'in'", "'from'", "'with'", "'back'", "'forward'", 
			"'track'", "'search'", "'as'", "'display'", "'export'", "'return'", "'select'", 
			"'connectdb'", "'db'", "'where'", "'include'", "'exclude'", "'nodes'", 
			"'edges'", "'limit'", "'time'", "'step'", "'ms'", "'s'", "'m'", "'type'", 
			"'name'", "'path'", "'dstip'", "'dstport'", "'srcip'", "'srcport'", "'pid'", 
			"'exename'", "'exepath'", "'cmdline'", "'optype'", "'id'", "'srcid'", 
			"'dstid'", "'starttime'", "'endtime'", "'amount'", "'process'", "'file'", 
			"'network'", "'read'", "'write'", "'execve'", "'null'", "'->'", "'='", 
			"'!='", "'>'", "'>='", "'<'", "'<='", "'like'", "'|'", "'&'", "'-'", 
			"'&&'", "'||'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "SEMICOLON", "COMMA", "ASTERISK", "LEFTBRACKET", "RIGHTBRACKET", 
			"LEFTBRACE", "RIGHTBRACE", "LEFTSQUARE", "RIGHTSQUARE", "AND", "OR", 
			"NOT", "IN", "FROM", "WITH", "BACK", "FORWARD", "TRACK", "SEARCH", "AS", 
			"DISPLAY", "EXPORT", "RETURN", "SELECT", "CONNECTDB", "DB", "WHERE", 
			"INCLUDE", "EXCLUDE", "NODES", "EDGES", "LIMIT", "TIME", "STEP", "MS", 
			"SECOND", "MINUTE", "TYPE", "NAME", "PATH", "DSTIP", "DSTPORT", "SRCIP", 
			"SRCPORT", "PID", "EXENAME", "EXEPATH", "CMDLINE", "OPTYPE", "IDSTR", 
			"SRCID", "DSTID", "STARTTIME", "ENDTIME", "AMOUNT", "PROCESS", "FILE", 
			"NETWORK", "READ", "WRITE", "EXECVE", "NULL", "ARROW", "EQ", "NEQ", "GT", 
			"GEQ", "LT", "LEQ", "LIKE", "UNION", "INTERSECTION", "DIFFERENCE", "LOGAND", 
			"LOGOR", "INT", "STRING", "ID", "NEWLINE", "WS", "COMMENT", "LINE_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "QueryParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


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

	public QueryParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class StartContext extends ParserRuleContext {
		public LinkedList<QueryStatement> stmts = new LinkedList<>();
		public List<ConnectStmtContext> connectStmt() {
			return getRuleContexts(ConnectStmtContext.class);
		}
		public ConnectStmtContext connectStmt(int i) {
			return getRuleContext(ConnectStmtContext.class,i);
		}
		public List<AssignStmtContext> assignStmt() {
			return getRuleContexts(AssignStmtContext.class);
		}
		public AssignStmtContext assignStmt(int i) {
			return getRuleContext(AssignStmtContext.class,i);
		}
		public List<SearchStmtContext> searchStmt() {
			return getRuleContexts(SearchStmtContext.class);
		}
		public SearchStmtContext searchStmt(int i) {
			return getRuleContext(SearchStmtContext.class,i);
		}
		public List<DisplayStmtContext> displayStmt() {
			return getRuleContexts(DisplayStmtContext.class);
		}
		public DisplayStmtContext displayStmt(int i) {
			return getRuleContext(DisplayStmtContext.class,i);
		}
		public List<ExportStmtContext> exportStmt() {
			return getRuleContexts(ExportStmtContext.class);
		}
		public ExportStmtContext exportStmt(int i) {
			return getRuleContext(ExportStmtContext.class,i);
		}
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterStart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitStart(this);
		}
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 19)) & ~0x3f) == 0 && ((1L << (_la - 19)) & ((1L << (SEARCH - 19)) | (1L << (DISPLAY - 19)) | (1L << (EXPORT - 19)) | (1L << (CONNECTDB - 19)) | (1L << (ID - 19)))) != 0)) {
				{
				setState(67);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case CONNECTDB:
					{
					setState(62);
					connectStmt();
					}
					break;
				case ID:
					{
					setState(63);
					assignStmt();
					}
					break;
				case SEARCH:
					{
					setState(64);
					searchStmt();
					}
					break;
				case DISPLAY:
					{
					setState(65);
					displayStmt();
					}
					break;
				case EXPORT:
					{
					setState(66);
					exportStmt();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(71);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConnectStmtContext extends ParserRuleContext {
		public Token STRING;
		public TerminalNode CONNECTDB() { return getToken(QueryParser.CONNECTDB, 0); }
		public TerminalNode STRING() { return getToken(QueryParser.STRING, 0); }
		public TerminalNode SEMICOLON() { return getToken(QueryParser.SEMICOLON, 0); }
		public ConnectStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_connectStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterConnectStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitConnectStmt(this);
		}
	}

	public final ConnectStmtContext connectStmt() throws RecognitionException {
		ConnectStmtContext _localctx = new ConnectStmtContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_connectStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			match(CONNECTDB);
			setState(73);
			((ConnectStmtContext)_localctx).STRING = match(STRING);
			setState(74);
			match(SEMICOLON);

			    String str = (((ConnectStmtContext)_localctx).STRING!=null?((ConnectStmtContext)_localctx).STRING.getText():null);
			    ((StartContext)getInvokingContext(0)).stmts.add(new ConnectDbStatement(exeCtx, str.substring(1, str.length() - 1)));

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignStmtContext extends ParserRuleContext {
		public Token ID;
		public GraphExprContext graphExpr;
		public SearchExprContext searchExpr;
		public TerminalNode ID() { return getToken(QueryParser.ID, 0); }
		public TerminalNode EQ() { return getToken(QueryParser.EQ, 0); }
		public GraphExprContext graphExpr() {
			return getRuleContext(GraphExprContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(QueryParser.SEMICOLON, 0); }
		public SearchExprContext searchExpr() {
			return getRuleContext(SearchExprContext.class,0);
		}
		public AssignStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterAssignStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitAssignStmt(this);
		}
	}

	public final AssignStmtContext assignStmt() throws RecognitionException {
		AssignStmtContext _localctx = new AssignStmtContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_assignStmt);
		try {
			setState(89);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(77);
				((AssignStmtContext)_localctx).ID = match(ID);
				setState(78);
				match(EQ);
				setState(79);
				((AssignStmtContext)_localctx).graphExpr = graphExpr(0);
				setState(80);
				match(SEMICOLON);

				    ((StartContext)getInvokingContext(0)).stmts.add(new AssignStatement(exeCtx, (((AssignStmtContext)_localctx).ID!=null?((AssignStmtContext)_localctx).ID.getText():null), ((AssignStmtContext)_localctx).graphExpr.gq));
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(83);
				((AssignStmtContext)_localctx).ID = match(ID);
				setState(84);
				match(EQ);
				setState(85);
				((AssignStmtContext)_localctx).searchExpr = searchExpr();
				setState(86);
				match(SEMICOLON);

				        ((StartContext)getInvokingContext(0)).stmts.add(new SearchStatement(exeCtx, (((AssignStmtContext)_localctx).ID!=null?((AssignStmtContext)_localctx).ID.getText():null), ((AssignStmtContext)_localctx).searchExpr.sc));
				    
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SearchExprContext extends ParserRuleContext {
		public SearchConstraints sc;
		public DataSourceContext dataSource;
		public NodeMatchTermContext nodeMatchTerm;
		public RelatTermContext relatTerm;
		public TerminalNode SEARCH() { return getToken(QueryParser.SEARCH, 0); }
		public TerminalNode FROM() { return getToken(QueryParser.FROM, 0); }
		public DataSourceContext dataSource() {
			return getRuleContext(DataSourceContext.class,0);
		}
		public NodeMatchTermContext nodeMatchTerm() {
			return getRuleContext(NodeMatchTermContext.class,0);
		}
		public RelatTermContext relatTerm() {
			return getRuleContext(RelatTermContext.class,0);
		}
		public SearchExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_searchExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterSearchExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitSearchExpr(this);
		}
	}

	public final SearchExprContext searchExpr() throws RecognitionException {
		SearchExprContext _localctx = new SearchExprContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_searchExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			match(SEARCH);
			setState(92);
			match(FROM);
			setState(93);
			((SearchExprContext)_localctx).dataSource = dataSource();
			setState(94);
			((SearchExprContext)_localctx).nodeMatchTerm = nodeMatchTerm();
			setState(95);
			((SearchExprContext)_localctx).relatTerm = relatTerm();

			        ((SearchExprContext)_localctx).sc =  new SearchConstraints(((SearchExprContext)_localctx).dataSource.source);
			        _localctx.sc.setNodeConstraints(((SearchExprContext)_localctx).nodeMatchTerm.cm);
			        _localctx.sc.setEdges(((SearchExprContext)_localctx).relatTerm.relations);
			        _localctx.sc.setEdgeConstraints(((SearchExprContext)_localctx).relatTerm.opts);
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SearchStmtContext extends ParserRuleContext {
		public SearchConstraints sc;
		public DataSourceContext dataSource;
		public NodeMatchTermContext nodeMatchTerm;
		public RelatTermContext relatTerm;
		public ReturnTermContext returnTerm;
		public TerminalNode SEARCH() { return getToken(QueryParser.SEARCH, 0); }
		public TerminalNode FROM() { return getToken(QueryParser.FROM, 0); }
		public DataSourceContext dataSource() {
			return getRuleContext(DataSourceContext.class,0);
		}
		public NodeMatchTermContext nodeMatchTerm() {
			return getRuleContext(NodeMatchTermContext.class,0);
		}
		public RelatTermContext relatTerm() {
			return getRuleContext(RelatTermContext.class,0);
		}
		public ReturnTermContext returnTerm() {
			return getRuleContext(ReturnTermContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(QueryParser.SEMICOLON, 0); }
		public SearchStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_searchStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterSearchStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitSearchStmt(this);
		}
	}

	public final SearchStmtContext searchStmt() throws RecognitionException {
		SearchStmtContext _localctx = new SearchStmtContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_searchStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			match(SEARCH);
			setState(99);
			match(FROM);
			setState(100);
			((SearchStmtContext)_localctx).dataSource = dataSource();
			setState(101);
			((SearchStmtContext)_localctx).nodeMatchTerm = nodeMatchTerm();
			setState(102);
			((SearchStmtContext)_localctx).relatTerm = relatTerm();
			setState(103);
			((SearchStmtContext)_localctx).returnTerm = returnTerm();
			setState(104);
			match(SEMICOLON);

			        ((SearchStmtContext)_localctx).sc =  new SearchConstraints(((SearchStmtContext)_localctx).dataSource.source);
			        _localctx.sc.setNodeConstraints(((SearchStmtContext)_localctx).nodeMatchTerm.cm);
			        _localctx.sc.setEdges(((SearchStmtContext)_localctx).relatTerm.relations);
			        _localctx.sc.setEdgeConstraints(((SearchStmtContext)_localctx).relatTerm.opts);
			        _localctx.sc.setReturnName(((SearchStmtContext)_localctx).returnTerm.name, ((SearchStmtContext)_localctx).returnTerm.isDisplay);
			        ((StartContext)getInvokingContext(0)).stmts.add(new SearchStatement(exeCtx, _localctx.sc));
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DisplayStmtContext extends ParserRuleContext {
		public GraphExprContext graphExpr;
		public TerminalNode DISPLAY() { return getToken(QueryParser.DISPLAY, 0); }
		public GraphExprContext graphExpr() {
			return getRuleContext(GraphExprContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(QueryParser.SEMICOLON, 0); }
		public DisplayStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_displayStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterDisplayStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitDisplayStmt(this);
		}
	}

	public final DisplayStmtContext displayStmt() throws RecognitionException {
		DisplayStmtContext _localctx = new DisplayStmtContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_displayStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			match(DISPLAY);
			setState(108);
			((DisplayStmtContext)_localctx).graphExpr = graphExpr(0);
			setState(109);
			match(SEMICOLON);

			    ((StartContext)getInvokingContext(0)).stmts.add(new DisplayStatement(exeCtx, ((DisplayStmtContext)_localctx).graphExpr.gq));

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExportStmtContext extends ParserRuleContext {
		public GraphExprContext graphExpr;
		public Token STRING;
		public TerminalNode EXPORT() { return getToken(QueryParser.EXPORT, 0); }
		public GraphExprContext graphExpr() {
			return getRuleContext(GraphExprContext.class,0);
		}
		public TerminalNode AS() { return getToken(QueryParser.AS, 0); }
		public TerminalNode STRING() { return getToken(QueryParser.STRING, 0); }
		public TerminalNode SEMICOLON() { return getToken(QueryParser.SEMICOLON, 0); }
		public ExportStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exportStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterExportStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitExportStmt(this);
		}
	}

	public final ExportStmtContext exportStmt() throws RecognitionException {
		ExportStmtContext _localctx = new ExportStmtContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_exportStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			match(EXPORT);
			setState(113);
			((ExportStmtContext)_localctx).graphExpr = graphExpr(0);
			setState(114);
			match(AS);
			setState(115);
			((ExportStmtContext)_localctx).STRING = match(STRING);
			setState(116);
			match(SEMICOLON);

			    String str = (((ExportStmtContext)_localctx).STRING!=null?((ExportStmtContext)_localctx).STRING.getText():null);
			    ((StartContext)getInvokingContext(0)).stmts.add(new ExportStatement(exeCtx, ((ExportStmtContext)_localctx).graphExpr.gq, str.substring(1, str.length() - 1)));

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NodeMatchTermContext extends ParserRuleContext {
		public HashMap<String, ConstraintExpression> cm = new HashMap<String, ConstraintExpression>();
		public TerminalNode WHERE() { return getToken(QueryParser.WHERE, 0); }
		public NodeAttributesContext nodeAttributes() {
			return getRuleContext(NodeAttributesContext.class,0);
		}
		public NodeMatchTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nodeMatchTerm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterNodeMatchTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitNodeMatchTerm(this);
		}
	}

	public final NodeMatchTermContext nodeMatchTerm() throws RecognitionException {
		NodeMatchTermContext _localctx = new NodeMatchTermContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_nodeMatchTerm);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			match(WHERE);
			setState(120);
			nodeAttributes(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelatTermContext extends ParserRuleContext {
		public ArrayList<ArrayList<String>> relations = new ArrayList<ArrayList<String>>();
		public ArrayList<List<String>> opts = new ArrayList<List<String>>();
		public TerminalNode WITH() { return getToken(QueryParser.WITH, 0); }
		public RelatConstContext relatConst() {
			return getRuleContext(RelatConstContext.class,0);
		}
		public RelatTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relatTerm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterRelatTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitRelatTerm(this);
		}
	}

	public final RelatTermContext relatTerm() throws RecognitionException {
		RelatTermContext _localctx = new RelatTermContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_relatTerm);
		try {
			setState(125);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WITH:
				enterOuterAlt(_localctx, 1);
				{
				setState(122);
				match(WITH);
				setState(123);
				relatConst(0);
				}
				break;
			case SEMICOLON:
			case RETURN:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnTermContext extends ParserRuleContext {
		public String name;
		public Boolean isDisplay;
		public Token ID;
		public TerminalNode RETURN() { return getToken(QueryParser.RETURN, 0); }
		public TerminalNode ASTERISK() { return getToken(QueryParser.ASTERISK, 0); }
		public TerminalNode AS() { return getToken(QueryParser.AS, 0); }
		public TerminalNode ID() { return getToken(QueryParser.ID, 0); }
		public ReturnTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnTerm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterReturnTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitReturnTerm(this);
		}
	}

	public final ReturnTermContext returnTerm() throws RecognitionException {
		ReturnTermContext _localctx = new ReturnTermContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_returnTerm);
		try {
			setState(135);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(127);
				match(RETURN);
				setState(128);
				match(ASTERISK);

				        ((ReturnTermContext)_localctx).name =  "deprecated!";
				        ((ReturnTermContext)_localctx).isDisplay =  true;
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(130);
				match(RETURN);
				setState(131);
				match(ASTERISK);
				setState(132);
				match(AS);
				setState(133);
				((ReturnTermContext)_localctx).ID = match(ID);

				        ((ReturnTermContext)_localctx).name =  (((ReturnTermContext)_localctx).ID!=null?((ReturnTermContext)_localctx).ID.getText():null);
				        ((ReturnTermContext)_localctx).isDisplay =  false;
				    
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NodeAttributesContext extends ParserRuleContext {
		public String name;
		public ConstraintExpression ce;
		public NodeAttributesContext na1;
		public Token ID;
		public ExprContext expr;
		public NodeAttributesContext na2;
		public TerminalNode ID() { return getToken(QueryParser.ID, 0); }
		public TerminalNode LEFTBRACE() { return getToken(QueryParser.LEFTBRACE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode RIGHTBRACE() { return getToken(QueryParser.RIGHTBRACE, 0); }
		public TerminalNode COMMA() { return getToken(QueryParser.COMMA, 0); }
		public List<NodeAttributesContext> nodeAttributes() {
			return getRuleContexts(NodeAttributesContext.class);
		}
		public NodeAttributesContext nodeAttributes(int i) {
			return getRuleContext(NodeAttributesContext.class,i);
		}
		public NodeAttributesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nodeAttributes; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterNodeAttributes(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitNodeAttributes(this);
		}
	}

	public final NodeAttributesContext nodeAttributes() throws RecognitionException {
		return nodeAttributes(0);
	}

	private NodeAttributesContext nodeAttributes(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		NodeAttributesContext _localctx = new NodeAttributesContext(_ctx, _parentState);
		NodeAttributesContext _prevctx = _localctx;
		int _startState = 20;
		enterRecursionRule(_localctx, 20, RULE_nodeAttributes, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(138);
			((NodeAttributesContext)_localctx).ID = match(ID);
			setState(139);
			match(LEFTBRACE);
			setState(140);
			((NodeAttributesContext)_localctx).expr = expr(0);
			setState(141);
			match(RIGHTBRACE);

			        ((NodeMatchTermContext)getInvokingContext(7)).cm.put((((NodeAttributesContext)_localctx).ID!=null?((NodeAttributesContext)_localctx).ID.getText():null), ((NodeAttributesContext)_localctx).expr.e);
			    
			}
			_ctx.stop = _input.LT(-1);
			setState(149);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new NodeAttributesContext(_parentctx, _parentState);
					_localctx.na1 = _prevctx;
					_localctx.na1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_nodeAttributes);
					setState(144);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(145);
					match(COMMA);
					setState(146);
					((NodeAttributesContext)_localctx).na2 = nodeAttributes(3);
					}
					} 
				}
				setState(151);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class LogicOptContext extends ParserRuleContext {
		public String opt;
		public String time;
		public String unit;
		public Token INT;
		public Token SECOND;
		public Token MINUTE;
		public Token MS;
		public TerminalNode LOGAND() { return getToken(QueryParser.LOGAND, 0); }
		public TerminalNode LEFTSQUARE() { return getToken(QueryParser.LEFTSQUARE, 0); }
		public TerminalNode LT() { return getToken(QueryParser.LT, 0); }
		public TerminalNode INT() { return getToken(QueryParser.INT, 0); }
		public TerminalNode SECOND() { return getToken(QueryParser.SECOND, 0); }
		public TerminalNode RIGHTSQUARE() { return getToken(QueryParser.RIGHTSQUARE, 0); }
		public TerminalNode MINUTE() { return getToken(QueryParser.MINUTE, 0); }
		public TerminalNode MS() { return getToken(QueryParser.MS, 0); }
		public TerminalNode LOGOR() { return getToken(QueryParser.LOGOR, 0); }
		public LogicOptContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicOpt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterLogicOpt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitLogicOpt(this);
		}
	}

	public final LogicOptContext logicOpt() throws RecognitionException {
		LogicOptContext _localctx = new LogicOptContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_logicOpt);
		try {
			setState(175);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(152);
				match(LOGAND);
				setState(153);
				match(LEFTSQUARE);
				setState(154);
				match(LT);
				setState(155);
				((LogicOptContext)_localctx).INT = match(INT);
				setState(156);
				((LogicOptContext)_localctx).SECOND = match(SECOND);
				setState(157);
				match(RIGHTSQUARE);
				((LogicOptContext)_localctx).opt = "and"; ((LogicOptContext)_localctx).time = (((LogicOptContext)_localctx).INT!=null?((LogicOptContext)_localctx).INT.getText():null); ((LogicOptContext)_localctx).unit = (((LogicOptContext)_localctx).SECOND!=null?((LogicOptContext)_localctx).SECOND.getText():null);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(159);
				match(LOGAND);
				setState(160);
				match(LEFTSQUARE);
				setState(161);
				match(LT);
				setState(162);
				((LogicOptContext)_localctx).INT = match(INT);
				setState(163);
				((LogicOptContext)_localctx).MINUTE = match(MINUTE);
				setState(164);
				match(RIGHTSQUARE);
				((LogicOptContext)_localctx).opt = "and"; ((LogicOptContext)_localctx).time = (((LogicOptContext)_localctx).INT!=null?((LogicOptContext)_localctx).INT.getText():null); ((LogicOptContext)_localctx).unit = (((LogicOptContext)_localctx).MINUTE!=null?((LogicOptContext)_localctx).MINUTE.getText():null);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(166);
				match(LOGAND);
				setState(167);
				match(LEFTSQUARE);
				setState(168);
				match(LT);
				setState(169);
				((LogicOptContext)_localctx).INT = match(INT);
				setState(170);
				((LogicOptContext)_localctx).MS = match(MS);
				setState(171);
				match(RIGHTSQUARE);
				((LogicOptContext)_localctx).opt = "and"; ((LogicOptContext)_localctx).time = (((LogicOptContext)_localctx).INT!=null?((LogicOptContext)_localctx).INT.getText():null); ((LogicOptContext)_localctx).unit = (((LogicOptContext)_localctx).MS!=null?((LogicOptContext)_localctx).MS.getText():null);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(173);
				match(LOGOR);
				((LogicOptContext)_localctx).opt = "or"; ((LogicOptContext)_localctx).time = "0"; ((LogicOptContext)_localctx).unit = "null";
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelatConstContext extends ParserRuleContext {
		public RelatExprContext relatExpr;
		public LogicOptContext logicOpt;
		public RelatExprContext relatExpr() {
			return getRuleContext(RelatExprContext.class,0);
		}
		public List<RelatConstContext> relatConst() {
			return getRuleContexts(RelatConstContext.class);
		}
		public RelatConstContext relatConst(int i) {
			return getRuleContext(RelatConstContext.class,i);
		}
		public LogicOptContext logicOpt() {
			return getRuleContext(LogicOptContext.class,0);
		}
		public RelatConstContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relatConst; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterRelatConst(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitRelatConst(this);
		}
	}

	public final RelatConstContext relatConst() throws RecognitionException {
		return relatConst(0);
	}

	private RelatConstContext relatConst(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		RelatConstContext _localctx = new RelatConstContext(_ctx, _parentState);
		RelatConstContext _prevctx = _localctx;
		int _startState = 24;
		enterRecursionRule(_localctx, 24, RULE_relatConst, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(178);
			((RelatConstContext)_localctx).relatExpr = relatExpr();

			        ((RelatTermContext)getInvokingContext(8)).relations.add(((RelatConstContext)_localctx).relatExpr.relation);
			    
			}
			_ctx.stop = _input.LT(-1);
			setState(188);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new RelatConstContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_relatConst);
					setState(181);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(182);
					((RelatConstContext)_localctx).logicOpt = logicOpt();
					setState(183);
					relatConst(3);

					                  List<String> al = Arrays.asList(((RelatConstContext)_localctx).logicOpt.opt, ((RelatConstContext)_localctx).logicOpt.time, ((RelatConstContext)_localctx).logicOpt.unit);
					                  ((RelatTermContext)getInvokingContext(8)).opts.add(al);
					              
					}
					} 
				}
				setState(190);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class RelatExprContext extends ParserRuleContext {
		public ArrayList<String> relation;
		public Token id1;
		public Token id2;
		public OptTypeContext optType;
		public TerminalNode ARROW() { return getToken(QueryParser.ARROW, 0); }
		public List<TerminalNode> ID() { return getTokens(QueryParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(QueryParser.ID, i);
		}
		public TerminalNode LEFTSQUARE() { return getToken(QueryParser.LEFTSQUARE, 0); }
		public OptTypeContext optType() {
			return getRuleContext(OptTypeContext.class,0);
		}
		public TerminalNode RIGHTSQUARE() { return getToken(QueryParser.RIGHTSQUARE, 0); }
		public RelatExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relatExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterRelatExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitRelatExpr(this);
		}
	}

	public final RelatExprContext relatExpr() throws RecognitionException {
		RelatExprContext _localctx = new RelatExprContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_relatExpr);
		int _la;
		try {
			setState(205);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(191);
				((RelatExprContext)_localctx).id1 = match(ID);
				setState(192);
				match(ARROW);
				setState(193);
				((RelatExprContext)_localctx).id2 = match(ID);
				((RelatExprContext)_localctx).relation =  new ArrayList<>(Arrays.asList((((RelatExprContext)_localctx).id1!=null?((RelatExprContext)_localctx).id1.getText():null), "null", (((RelatExprContext)_localctx).id2!=null?((RelatExprContext)_localctx).id2.getText():null)));
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(195);
				((RelatExprContext)_localctx).id1 = match(ID);
				setState(200);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LEFTSQUARE) {
					{
					setState(196);
					match(LEFTSQUARE);
					setState(197);
					((RelatExprContext)_localctx).optType = optType();
					setState(198);
					match(RIGHTSQUARE);
					}
				}

				setState(202);
				match(ARROW);
				setState(203);
				((RelatExprContext)_localctx).id2 = match(ID);
				((RelatExprContext)_localctx).relation =  new ArrayList<>(Arrays.asList((((RelatExprContext)_localctx).id1!=null?((RelatExprContext)_localctx).id1.getText():null), (((RelatExprContext)_localctx).optType!=null?_input.getText(((RelatExprContext)_localctx).optType.start,((RelatExprContext)_localctx).optType.stop):null), (((RelatExprContext)_localctx).id2!=null?((RelatExprContext)_localctx).id2.getText():null)));
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DataSourceContext extends ParserRuleContext {
		public String source;
		public Token ID;
		public TerminalNode DB() { return getToken(QueryParser.DB, 0); }
		public TerminalNode LEFTBRACKET() { return getToken(QueryParser.LEFTBRACKET, 0); }
		public TerminalNode ID() { return getToken(QueryParser.ID, 0); }
		public TerminalNode RIGHTBRACKET() { return getToken(QueryParser.RIGHTBRACKET, 0); }
		public DataSourceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dataSource; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterDataSource(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitDataSource(this);
		}
	}

	public final DataSourceContext dataSource() throws RecognitionException {
		DataSourceContext _localctx = new DataSourceContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_dataSource);
		try {
			setState(214);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DB:
				enterOuterAlt(_localctx, 1);
				{
				setState(207);
				match(DB);
				setState(208);
				match(LEFTBRACKET);
				setState(209);
				((DataSourceContext)_localctx).ID = match(ID);
				setState(210);
				match(RIGHTBRACKET);

				        ((StartContext)getInvokingContext(0)).stmts.add(new ConnectDbStatement(exeCtx, (((DataSourceContext)_localctx).ID!=null?((DataSourceContext)_localctx).ID.getText():null)));
				        ((DataSourceContext)_localctx).source =  null;
				    
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(212);
				((DataSourceContext)_localctx).ID = match(ID);

				        ((DataSourceContext)_localctx).source =  (((DataSourceContext)_localctx).ID!=null?((DataSourceContext)_localctx).ID.getText():null);
				    
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GraphExprContext extends ParserRuleContext {
		public GraphQuery gq;
		public GraphExprContext l;
		public GraphExprContext graphExpr;
		public Token ID;
		public TrackExprContext trackExpr;
		public GraphExprContext r;
		public TerminalNode LEFTBRACKET() { return getToken(QueryParser.LEFTBRACKET, 0); }
		public List<GraphExprContext> graphExpr() {
			return getRuleContexts(GraphExprContext.class);
		}
		public GraphExprContext graphExpr(int i) {
			return getRuleContext(GraphExprContext.class,i);
		}
		public TerminalNode RIGHTBRACKET() { return getToken(QueryParser.RIGHTBRACKET, 0); }
		public TerminalNode ID() { return getToken(QueryParser.ID, 0); }
		public TrackExprContext trackExpr() {
			return getRuleContext(TrackExprContext.class,0);
		}
		public TerminalNode UNION() { return getToken(QueryParser.UNION, 0); }
		public TerminalNode INTERSECTION() { return getToken(QueryParser.INTERSECTION, 0); }
		public TerminalNode DIFFERENCE() { return getToken(QueryParser.DIFFERENCE, 0); }
		public GraphExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_graphExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterGraphExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitGraphExpr(this);
		}
	}

	public final GraphExprContext graphExpr() throws RecognitionException {
		return graphExpr(0);
	}

	private GraphExprContext graphExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		GraphExprContext _localctx = new GraphExprContext(_ctx, _parentState);
		GraphExprContext _prevctx = _localctx;
		int _startState = 30;
		enterRecursionRule(_localctx, 30, RULE_graphExpr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(227);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LEFTBRACKET:
				{
				setState(217);
				match(LEFTBRACKET);
				setState(218);
				((GraphExprContext)_localctx).graphExpr = graphExpr(0);
				setState(219);
				match(RIGHTBRACKET);
				 ((GraphExprContext)_localctx).gq =  ((GraphExprContext)_localctx).graphExpr.gq; 
				}
				break;
			case ID:
				{
				setState(222);
				((GraphExprContext)_localctx).ID = match(ID);
				 ((GraphExprContext)_localctx).gq =  new VariableGraphQuery(exeCtx, (((GraphExprContext)_localctx).ID!=null?((GraphExprContext)_localctx).ID.getText():null)); 
				}
				break;
			case BACK:
			case FORWARD:
			case SEARCH:
				{
				setState(224);
				((GraphExprContext)_localctx).trackExpr = trackExpr();
				 ((GraphExprContext)_localctx).gq =  ((GraphExprContext)_localctx).trackExpr.gq; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(246);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(244);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
					case 1:
						{
						_localctx = new GraphExprContext(_parentctx, _parentState);
						_localctx.l = _prevctx;
						_localctx.l = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_graphExpr);
						setState(229);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(230);
						match(UNION);
						setState(231);
						((GraphExprContext)_localctx).r = ((GraphExprContext)_localctx).graphExpr = graphExpr(5);
						 ((GraphExprContext)_localctx).gq =  new UnionGraphQuery(((GraphExprContext)_localctx).l.gq, ((GraphExprContext)_localctx).r.gq); 
						}
						break;
					case 2:
						{
						_localctx = new GraphExprContext(_parentctx, _parentState);
						_localctx.l = _prevctx;
						_localctx.l = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_graphExpr);
						setState(234);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(235);
						match(INTERSECTION);
						setState(236);
						((GraphExprContext)_localctx).r = ((GraphExprContext)_localctx).graphExpr = graphExpr(4);
						 ((GraphExprContext)_localctx).gq =  new IntersectionGraphQuery(((GraphExprContext)_localctx).l.gq, ((GraphExprContext)_localctx).r.gq); 
						}
						break;
					case 3:
						{
						_localctx = new GraphExprContext(_parentctx, _parentState);
						_localctx.l = _prevctx;
						_localctx.l = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_graphExpr);
						setState(239);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(240);
						match(DIFFERENCE);
						setState(241);
						((GraphExprContext)_localctx).r = ((GraphExprContext)_localctx).graphExpr = graphExpr(3);
						 ((GraphExprContext)_localctx).gq =  new DifferenceGraphQuery(((GraphExprContext)_localctx).l.gq, ((GraphExprContext)_localctx).r.gq); 
						}
						break;
					}
					} 
				}
				setState(248);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class TrackExprContext extends ParserRuleContext {
		public GraphQuery gq;
		public BackTrackConstraints bc;
		public BackTrack bt;
		public DirectionContext direction;
		public StartTermContext startTerm;
		public DataSourceContext dataSource;
		public GraphExprContext graphExpr;
		public DirectionContext direction() {
			return getRuleContext(DirectionContext.class,0);
		}
		public TerminalNode TRACK() { return getToken(QueryParser.TRACK, 0); }
		public StartTermContext startTerm() {
			return getRuleContext(StartTermContext.class,0);
		}
		public TerminalNode FROM() { return getToken(QueryParser.FROM, 0); }
		public DataSourceContext dataSource() {
			return getRuleContext(DataSourceContext.class,0);
		}
		public FilterTermContext filterTerm() {
			return getRuleContext(FilterTermContext.class,0);
		}
		public LimitTermContext limitTerm() {
			return getRuleContext(LimitTermContext.class,0);
		}
		public TerminalNode SEARCH() { return getToken(QueryParser.SEARCH, 0); }
		public GraphExprContext graphExpr() {
			return getRuleContext(GraphExprContext.class,0);
		}
		public TrackExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_trackExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterTrackExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitTrackExpr(this);
		}
	}

	public final TrackExprContext trackExpr() throws RecognitionException {
		TrackExprContext _localctx = new TrackExprContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_trackExpr);
		try {
			setState(266);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BACK:
			case FORWARD:
				enterOuterAlt(_localctx, 1);
				{
				setState(249);
				((TrackExprContext)_localctx).direction = direction();
				setState(250);
				match(TRACK);
				setState(251);
				((TrackExprContext)_localctx).startTerm = startTerm();
				setState(252);
				match(FROM);
				setState(253);
				((TrackExprContext)_localctx).dataSource = dataSource();

				        if (((TrackExprContext)_localctx).startTerm.graphID != null) {
				            // The POI is a graph
				            ((TrackExprContext)_localctx).bc =  new BackTrackConstraints(((TrackExprContext)_localctx).startTerm.graphID);
				        } else {
				            ((TrackExprContext)_localctx).bc =  new BackTrackConstraints(((TrackExprContext)_localctx).startTerm.ce);
				        }
				        _localctx.bc.setIgnoreConstraints(exeCtx.isIgnoreConstraints());
				    
				setState(255);
				filterTerm();
				setState(256);
				limitTerm();

				        if (((TrackExprContext)_localctx).dataSource.source == null) {
				            // The data source is a local graph
				            ((TrackExprContext)_localctx).bt =  exeCtx.isUseRecursive() ? new BackTrackRemoteRecursive() : new BackTrackRemote();
				        } else {
				            ((TrackExprContext)_localctx).bt =  new BackTrackLocal(exeCtx, ((TrackExprContext)_localctx).dataSource.source);
				        }
				        ((TrackExprContext)_localctx).gq =  new TrackGraphQuery(exeCtx, ((TrackExprContext)_localctx).direction.isBack, _localctx.bt, _localctx.bc);
				    
				}
				break;
			case SEARCH:
				enterOuterAlt(_localctx, 2);
				{
				setState(259);
				match(SEARCH);
				setState(260);
				match(FROM);
				setState(261);
				((TrackExprContext)_localctx).graphExpr = graphExpr(0);
				 ((TrackExprContext)_localctx).bc =  new BackTrackConstraints(); 
				setState(263);
				filterTerm();
				 ((TrackExprContext)_localctx).gq =  new SelectGraphQuery(((TrackExprContext)_localctx).graphExpr.gq, _localctx.bc); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DirectionContext extends ParserRuleContext {
		public boolean isBack;
		public TerminalNode BACK() { return getToken(QueryParser.BACK, 0); }
		public TerminalNode FORWARD() { return getToken(QueryParser.FORWARD, 0); }
		public DirectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_direction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterDirection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitDirection(this);
		}
	}

	public final DirectionContext direction() throws RecognitionException {
		DirectionContext _localctx = new DirectionContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_direction);
		try {
			setState(272);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BACK:
				enterOuterAlt(_localctx, 1);
				{
				setState(268);
				match(BACK);
				 ((DirectionContext)_localctx).isBack =  true; 
				}
				break;
			case FORWARD:
				enterOuterAlt(_localctx, 2);
				{
				setState(270);
				match(FORWARD);
				 ((DirectionContext)_localctx).isBack =  false; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StartTermContext extends ParserRuleContext {
		public ConstraintExpression ce;
		public String graphID;
		public Token ID;
		public ConstraintExprContext constraintExpr;
		public TerminalNode ID() { return getToken(QueryParser.ID, 0); }
		public ConstraintExprContext constraintExpr() {
			return getRuleContext(ConstraintExprContext.class,0);
		}
		public StartTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_startTerm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterStartTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitStartTerm(this);
		}
	}

	public final StartTermContext startTerm() throws RecognitionException {
		StartTermContext _localctx = new StartTermContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_startTerm);
		try {
			setState(279);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(274);
				((StartTermContext)_localctx).ID = match(ID);
				 ((StartTermContext)_localctx).graphID =  (((StartTermContext)_localctx).ID!=null?((StartTermContext)_localctx).ID.getText():null); 
				}
				break;
			case WHERE:
				enterOuterAlt(_localctx, 2);
				{
				setState(276);
				((StartTermContext)_localctx).constraintExpr = constraintExpr();
				 ((StartTermContext)_localctx).ce =  ((StartTermContext)_localctx).constraintExpr.e; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FilterTermContext extends ParserRuleContext {
		public TypeFilterTermContext i;
		public TypeFilterTermContext e;
		public TerminalNode INCLUDE() { return getToken(QueryParser.INCLUDE, 0); }
		public List<TypeFilterTermContext> typeFilterTerm() {
			return getRuleContexts(TypeFilterTermContext.class);
		}
		public TypeFilterTermContext typeFilterTerm(int i) {
			return getRuleContext(TypeFilterTermContext.class,i);
		}
		public TerminalNode EXCLUDE() { return getToken(QueryParser.EXCLUDE, 0); }
		public FilterTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_filterTerm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterFilterTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitFilterTerm(this);
		}
	}

	public final FilterTermContext filterTerm() throws RecognitionException {
		FilterTermContext _localctx = new FilterTermContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_filterTerm);
		try {
			setState(302);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(281);
				match(INCLUDE);
				setState(282);
				((FilterTermContext)_localctx).i = typeFilterTerm();

				               ((TrackExprContext)getInvokingContext(16)).bc.setNodeConstraints(((FilterTermContext)_localctx).i.n);
				               ((TrackExprContext)getInvokingContext(16)).bc.setEdgeConstraints(((FilterTermContext)_localctx).i.e);
				           
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(285);
				match(EXCLUDE);
				setState(286);
				((FilterTermContext)_localctx).e = typeFilterTerm();

				               if (((FilterTermContext)_localctx).e.n != null) ((TrackExprContext)getInvokingContext(16)).bc.setNodeConstraints(new NotExpression(((FilterTermContext)_localctx).e.n));
				               if (((FilterTermContext)_localctx).e.e != null) ((TrackExprContext)getInvokingContext(16)).bc.setEdgeConstraints(new NotExpression(((FilterTermContext)_localctx).e.e));
				           
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(289);
				match(INCLUDE);
				setState(290);
				((FilterTermContext)_localctx).i = typeFilterTerm();
				setState(291);
				match(EXCLUDE);
				setState(292);
				((FilterTermContext)_localctx).e = typeFilterTerm();

				               ((TrackExprContext)getInvokingContext(16)).bc.setNodeConstraints(combine(((FilterTermContext)_localctx).i.n, ((FilterTermContext)_localctx).e.n));
				               ((TrackExprContext)getInvokingContext(16)).bc.setEdgeConstraints(combine(((FilterTermContext)_localctx).i.e, ((FilterTermContext)_localctx).e.e));
				           
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(295);
				match(EXCLUDE);
				setState(296);
				((FilterTermContext)_localctx).e = typeFilterTerm();
				setState(297);
				match(INCLUDE);
				setState(298);
				((FilterTermContext)_localctx).i = typeFilterTerm();

				               ((TrackExprContext)getInvokingContext(16)).bc.setNodeConstraints(combine(((FilterTermContext)_localctx).i.n, ((FilterTermContext)_localctx).e.n));
				               ((TrackExprContext)getInvokingContext(16)).bc.setEdgeConstraints(combine(((FilterTermContext)_localctx).i.e, ((FilterTermContext)_localctx).e.e));
				           
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeFilterTermContext extends ParserRuleContext {
		public ConstraintExpression n;
		public ConstraintExpression e;
		public ConstraintExprContext ne;
		public ConstraintExprContext ee;
		public TerminalNode NODES() { return getToken(QueryParser.NODES, 0); }
		public List<ConstraintExprContext> constraintExpr() {
			return getRuleContexts(ConstraintExprContext.class);
		}
		public ConstraintExprContext constraintExpr(int i) {
			return getRuleContext(ConstraintExprContext.class,i);
		}
		public TerminalNode EDGES() { return getToken(QueryParser.EDGES, 0); }
		public TerminalNode COMMA() { return getToken(QueryParser.COMMA, 0); }
		public TypeFilterTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeFilterTerm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterTypeFilterTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitTypeFilterTerm(this);
		}
	}

	public final TypeFilterTermContext typeFilterTerm() throws RecognitionException {
		TypeFilterTermContext _localctx = new TypeFilterTermContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_typeFilterTerm);
		try {
			setState(326);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(304);
				match(NODES);
				setState(305);
				((TypeFilterTermContext)_localctx).ne = constraintExpr();
				 ((TypeFilterTermContext)_localctx).n =  ((TypeFilterTermContext)_localctx).ne.e; 
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(308);
				match(EDGES);
				setState(309);
				((TypeFilterTermContext)_localctx).ee = constraintExpr();
				 ((TypeFilterTermContext)_localctx).e =  ((TypeFilterTermContext)_localctx).ee.e; 
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(312);
				match(NODES);
				setState(313);
				((TypeFilterTermContext)_localctx).ne = constraintExpr();
				setState(314);
				match(COMMA);
				setState(315);
				match(EDGES);
				setState(316);
				((TypeFilterTermContext)_localctx).ee = constraintExpr();
				 ((TypeFilterTermContext)_localctx).n =  ((TypeFilterTermContext)_localctx).ne.e; ((TypeFilterTermContext)_localctx).e =  ((TypeFilterTermContext)_localctx).ee.e; 
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(319);
				match(EDGES);
				setState(320);
				((TypeFilterTermContext)_localctx).ee = constraintExpr();
				setState(321);
				match(COMMA);
				setState(322);
				match(NODES);
				setState(323);
				((TypeFilterTermContext)_localctx).ne = constraintExpr();
				 ((TypeFilterTermContext)_localctx).n =  ((TypeFilterTermContext)_localctx).ne.e; ((TypeFilterTermContext)_localctx).e =  ((TypeFilterTermContext)_localctx).ee.e; 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LimitTermContext extends ParserRuleContext {
		public Token s;
		public Token t;
		public TerminalNode LIMIT() { return getToken(QueryParser.LIMIT, 0); }
		public TerminalNode STEP() { return getToken(QueryParser.STEP, 0); }
		public List<TerminalNode> INT() { return getTokens(QueryParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(QueryParser.INT, i);
		}
		public TerminalNode TIME() { return getToken(QueryParser.TIME, 0); }
		public TerminalNode COMMA() { return getToken(QueryParser.COMMA, 0); }
		public LimitTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_limitTerm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterLimitTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitLimitTerm(this);
		}
	}

	public final LimitTermContext limitTerm() throws RecognitionException {
		LimitTermContext _localctx = new LimitTermContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_limitTerm);
		try {
			setState(351);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(328);
				match(LIMIT);
				setState(329);
				match(STEP);
				setState(330);
				((LimitTermContext)_localctx).s = match(INT);
				 ((TrackExprContext)getInvokingContext(16)).bc.setStepConstraint((((LimitTermContext)_localctx).s!=null?Integer.valueOf(((LimitTermContext)_localctx).s.getText()):0)); 
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(332);
				match(LIMIT);
				setState(333);
				match(TIME);
				setState(334);
				((LimitTermContext)_localctx).t = match(INT);
				 ((TrackExprContext)getInvokingContext(16)).bc.setTimeConstraintSecs((((LimitTermContext)_localctx).t!=null?Integer.valueOf(((LimitTermContext)_localctx).t.getText()):0)); 
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(336);
				match(LIMIT);
				setState(337);
				match(STEP);
				setState(338);
				((LimitTermContext)_localctx).s = match(INT);
				setState(339);
				match(COMMA);
				setState(340);
				match(TIME);
				setState(341);
				((LimitTermContext)_localctx).t = match(INT);

				              ((TrackExprContext)getInvokingContext(16)).bc.setStepConstraint((((LimitTermContext)_localctx).s!=null?Integer.valueOf(((LimitTermContext)_localctx).s.getText()):0));
				              ((TrackExprContext)getInvokingContext(16)).bc.setTimeConstraintSecs((((LimitTermContext)_localctx).t!=null?Integer.valueOf(((LimitTermContext)_localctx).t.getText()):0));
				          
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(343);
				match(LIMIT);
				setState(344);
				match(TIME);
				setState(345);
				((LimitTermContext)_localctx).t = match(INT);
				setState(346);
				match(COMMA);
				setState(347);
				match(STEP);
				setState(348);
				((LimitTermContext)_localctx).s = match(INT);

				              ((TrackExprContext)getInvokingContext(16)).bc.setStepConstraint((((LimitTermContext)_localctx).s!=null?Integer.valueOf(((LimitTermContext)_localctx).s.getText()):0));
				              ((TrackExprContext)getInvokingContext(16)).bc.setTimeConstraintSecs((((LimitTermContext)_localctx).t!=null?Integer.valueOf(((LimitTermContext)_localctx).t.getText()):0));
				          
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstraintExprContext extends ParserRuleContext {
		public ConstraintExpression e;
		public ExprContext expr;
		public TerminalNode WHERE() { return getToken(QueryParser.WHERE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ConstraintExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraintExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterConstraintExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitConstraintExpr(this);
		}
	}

	public final ConstraintExprContext constraintExpr() throws RecognitionException {
		ConstraintExprContext _localctx = new ConstraintExprContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_constraintExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(353);
			match(WHERE);
			setState(354);
			((ConstraintExprContext)_localctx).expr = expr(0);
			 ((ConstraintExprContext)_localctx).e =  ((ConstraintExprContext)_localctx).expr.e; 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public ConstraintExpression e;
		public ExprContext e1;
		public ExprContext expr;
		public BinaryExprContext binaryExpr;
		public ExprContext e2;
		public TerminalNode LEFTBRACKET() { return getToken(QueryParser.LEFTBRACKET, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode RIGHTBRACKET() { return getToken(QueryParser.RIGHTBRACKET, 0); }
		public TerminalNode NOT() { return getToken(QueryParser.NOT, 0); }
		public BinaryExprContext binaryExpr() {
			return getRuleContext(BinaryExprContext.class,0);
		}
		public TerminalNode AND() { return getToken(QueryParser.AND, 0); }
		public TerminalNode OR() { return getToken(QueryParser.OR, 0); }
		public TerminalNode COMMA() { return getToken(QueryParser.COMMA, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 46;
		enterRecursionRule(_localctx, 46, RULE_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(370);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LEFTBRACKET:
				{
				setState(358);
				match(LEFTBRACKET);
				setState(359);
				((ExprContext)_localctx).expr = expr(0);
				setState(360);
				match(RIGHTBRACKET);
				 ((ExprContext)_localctx).e =  ((ExprContext)_localctx).expr.e; 
				}
				break;
			case NOT:
				{
				setState(363);
				match(NOT);
				setState(364);
				((ExprContext)_localctx).e1 = ((ExprContext)_localctx).expr = expr(5);
				 ((ExprContext)_localctx).e =  new NotExpression(((ExprContext)_localctx).e1.e); 
				}
				break;
			case TYPE:
			case NAME:
			case PATH:
			case DSTIP:
			case DSTPORT:
			case SRCIP:
			case SRCPORT:
			case PID:
			case EXENAME:
			case EXEPATH:
			case CMDLINE:
			case OPTYPE:
			case IDSTR:
			case SRCID:
			case DSTID:
			case STARTTIME:
			case ENDTIME:
			case AMOUNT:
				{
				setState(367);
				((ExprContext)_localctx).binaryExpr = binaryExpr();
				 ((ExprContext)_localctx).e =  ((ExprContext)_localctx).binaryExpr.b; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(389);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(387);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(372);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(373);
						match(AND);
						setState(374);
						((ExprContext)_localctx).e2 = ((ExprContext)_localctx).expr = expr(5);
						 ((ExprContext)_localctx).e =  new AndExpression(((ExprContext)_localctx).e1.e, ((ExprContext)_localctx).e2.e); 
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(377);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(378);
						match(OR);
						setState(379);
						((ExprContext)_localctx).e2 = ((ExprContext)_localctx).expr = expr(4);
						 ((ExprContext)_localctx).e =  new OrExpression(((ExprContext)_localctx).e1.e, ((ExprContext)_localctx).e2.e); 
						}
						break;
					case 3:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						_localctx.e1 = _prevctx;
						_localctx.e1 = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(382);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(383);
						match(COMMA);
						setState(384);
						((ExprContext)_localctx).e2 = ((ExprContext)_localctx).expr = expr(3);
						 ((ExprContext)_localctx).e =  new AndExpression(((ExprContext)_localctx).e1.e, ((ExprContext)_localctx).e2.e); 
						}
						break;
					}
					} 
				}
				setState(391);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class BinaryExprContext extends ParserRuleContext {
		public BinaryExpression b;
		public StrIdContext strId;
		public EqOpContext eqOp;
		public EntityTypeContext entityType;
		public Token STRING;
		public NumIdContext numId;
		public NumOpContext numOp;
		public Token INT;
		public StrIdContext strId() {
			return getRuleContext(StrIdContext.class,0);
		}
		public EqOpContext eqOp() {
			return getRuleContext(EqOpContext.class,0);
		}
		public EntityTypeContext entityType() {
			return getRuleContext(EntityTypeContext.class,0);
		}
		public TerminalNode STRING() { return getToken(QueryParser.STRING, 0); }
		public NumIdContext numId() {
			return getRuleContext(NumIdContext.class,0);
		}
		public NumOpContext numOp() {
			return getRuleContext(NumOpContext.class,0);
		}
		public TerminalNode INT() { return getToken(QueryParser.INT, 0); }
		public BinaryExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binaryExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterBinaryExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitBinaryExpr(this);
		}
	}

	public final BinaryExprContext binaryExpr() throws RecognitionException {
		BinaryExprContext _localctx = new BinaryExprContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_binaryExpr);
		try {
			setState(407);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(392);
				((BinaryExprContext)_localctx).strId = strId();
				setState(393);
				((BinaryExprContext)_localctx).eqOp = eqOp();
				setState(394);
				((BinaryExprContext)_localctx).entityType = entityType();

				              ((BinaryExprContext)_localctx).b =  new BinaryExpression((((BinaryExprContext)_localctx).strId!=null?_input.getText(((BinaryExprContext)_localctx).strId.start,((BinaryExprContext)_localctx).strId.stop):null), ((BinaryExprContext)_localctx).eqOp.v, (((BinaryExprContext)_localctx).entityType!=null?_input.getText(((BinaryExprContext)_localctx).entityType.start,((BinaryExprContext)_localctx).entityType.stop):null));
				    
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(397);
				((BinaryExprContext)_localctx).strId = strId();
				setState(398);
				((BinaryExprContext)_localctx).eqOp = eqOp();
				setState(399);
				((BinaryExprContext)_localctx).STRING = match(STRING);

				        String str = (((BinaryExprContext)_localctx).STRING!=null?((BinaryExprContext)_localctx).STRING.getText():null);
				        str = str.substring(1, str.length() - 1);
				        boolean isLike = ((BinaryExprContext)_localctx).eqOp.v == BinaryOperator.Like;
				        ((BinaryExprContext)_localctx).b =  new BinaryExpression((((BinaryExprContext)_localctx).strId!=null?_input.getText(((BinaryExprContext)_localctx).strId.start,((BinaryExprContext)_localctx).strId.stop):null), ((BinaryExprContext)_localctx).eqOp.v, isLike ? "%" + str + "%" : str);
				    
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(402);
				((BinaryExprContext)_localctx).numId = numId();
				setState(403);
				((BinaryExprContext)_localctx).numOp = numOp();
				setState(404);
				((BinaryExprContext)_localctx).INT = match(INT);
				 ((BinaryExprContext)_localctx).b =  new BinaryExpression((((BinaryExprContext)_localctx).numId!=null?_input.getText(((BinaryExprContext)_localctx).numId.start,((BinaryExprContext)_localctx).numId.stop):null), ((BinaryExprContext)_localctx).numOp.v, new BigDecimal((((BinaryExprContext)_localctx).INT!=null?((BinaryExprContext)_localctx).INT.getText():null))); 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StrIdContext extends ParserRuleContext {
		public TerminalNode TYPE() { return getToken(QueryParser.TYPE, 0); }
		public TerminalNode NAME() { return getToken(QueryParser.NAME, 0); }
		public TerminalNode PATH() { return getToken(QueryParser.PATH, 0); }
		public TerminalNode DSTIP() { return getToken(QueryParser.DSTIP, 0); }
		public TerminalNode SRCIP() { return getToken(QueryParser.SRCIP, 0); }
		public TerminalNode EXENAME() { return getToken(QueryParser.EXENAME, 0); }
		public TerminalNode EXEPATH() { return getToken(QueryParser.EXEPATH, 0); }
		public TerminalNode CMDLINE() { return getToken(QueryParser.CMDLINE, 0); }
		public TerminalNode OPTYPE() { return getToken(QueryParser.OPTYPE, 0); }
		public StrIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_strId; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterStrId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitStrId(this);
		}
	}

	public final StrIdContext strId() throws RecognitionException {
		StrIdContext _localctx = new StrIdContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_strId);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(409);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << TYPE) | (1L << NAME) | (1L << PATH) | (1L << DSTIP) | (1L << SRCIP) | (1L << EXENAME) | (1L << EXEPATH) | (1L << CMDLINE) | (1L << OPTYPE))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumIdContext extends ParserRuleContext {
		public TerminalNode IDSTR() { return getToken(QueryParser.IDSTR, 0); }
		public TerminalNode SRCID() { return getToken(QueryParser.SRCID, 0); }
		public TerminalNode DSTID() { return getToken(QueryParser.DSTID, 0); }
		public TerminalNode STARTTIME() { return getToken(QueryParser.STARTTIME, 0); }
		public TerminalNode ENDTIME() { return getToken(QueryParser.ENDTIME, 0); }
		public TerminalNode AMOUNT() { return getToken(QueryParser.AMOUNT, 0); }
		public TerminalNode PID() { return getToken(QueryParser.PID, 0); }
		public TerminalNode DSTPORT() { return getToken(QueryParser.DSTPORT, 0); }
		public TerminalNode SRCPORT() { return getToken(QueryParser.SRCPORT, 0); }
		public NumIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numId; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterNumId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitNumId(this);
		}
	}

	public final NumIdContext numId() throws RecognitionException {
		NumIdContext _localctx = new NumIdContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_numId);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(411);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DSTPORT) | (1L << SRCPORT) | (1L << PID) | (1L << IDSTR) | (1L << SRCID) | (1L << DSTID) | (1L << STARTTIME) | (1L << ENDTIME) | (1L << AMOUNT))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EntityTypeContext extends ParserRuleContext {
		public TerminalNode PROCESS() { return getToken(QueryParser.PROCESS, 0); }
		public TerminalNode FILE() { return getToken(QueryParser.FILE, 0); }
		public TerminalNode NETWORK() { return getToken(QueryParser.NETWORK, 0); }
		public EntityTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_entityType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterEntityType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitEntityType(this);
		}
	}

	public final EntityTypeContext entityType() throws RecognitionException {
		EntityTypeContext _localctx = new EntityTypeContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_entityType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(413);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PROCESS) | (1L << FILE) | (1L << NETWORK))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OptTypeContext extends ParserRuleContext {
		public TerminalNode READ() { return getToken(QueryParser.READ, 0); }
		public TerminalNode WRITE() { return getToken(QueryParser.WRITE, 0); }
		public TerminalNode EXECVE() { return getToken(QueryParser.EXECVE, 0); }
		public OptTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_optType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterOptType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitOptType(this);
		}
	}

	public final OptTypeContext optType() throws RecognitionException {
		OptTypeContext _localctx = new OptTypeContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_optType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(415);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << READ) | (1L << WRITE) | (1L << EXECVE))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EqOpContext extends ParserRuleContext {
		public BinaryOperator v;
		public TerminalNode EQ() { return getToken(QueryParser.EQ, 0); }
		public TerminalNode NEQ() { return getToken(QueryParser.NEQ, 0); }
		public TerminalNode LIKE() { return getToken(QueryParser.LIKE, 0); }
		public EqOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eqOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterEqOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitEqOp(this);
		}
	}

	public final EqOpContext eqOp() throws RecognitionException {
		EqOpContext _localctx = new EqOpContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_eqOp);
		try {
			setState(423);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case EQ:
				enterOuterAlt(_localctx, 1);
				{
				setState(417);
				match(EQ);
				 ((EqOpContext)_localctx).v =  BinaryOperator.Equal; 
				}
				break;
			case NEQ:
				enterOuterAlt(_localctx, 2);
				{
				setState(419);
				match(NEQ);
				 ((EqOpContext)_localctx).v =  BinaryOperator.NotEqual; 
				}
				break;
			case LIKE:
				enterOuterAlt(_localctx, 3);
				{
				setState(421);
				match(LIKE);
				 ((EqOpContext)_localctx).v =  BinaryOperator.Like; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumOpContext extends ParserRuleContext {
		public BinaryOperator v;
		public EqOpContext eqOp;
		public EqOpContext eqOp() {
			return getRuleContext(EqOpContext.class,0);
		}
		public TerminalNode GT() { return getToken(QueryParser.GT, 0); }
		public TerminalNode GEQ() { return getToken(QueryParser.GEQ, 0); }
		public TerminalNode LT() { return getToken(QueryParser.LT, 0); }
		public TerminalNode LEQ() { return getToken(QueryParser.LEQ, 0); }
		public NumOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).enterNumOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryParserListener ) ((QueryParserListener)listener).exitNumOp(this);
		}
	}

	public final NumOpContext numOp() throws RecognitionException {
		NumOpContext _localctx = new NumOpContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_numOp);
		try {
			setState(436);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case EQ:
			case NEQ:
			case LIKE:
				enterOuterAlt(_localctx, 1);
				{
				setState(425);
				((NumOpContext)_localctx).eqOp = eqOp();
				 ((NumOpContext)_localctx).v =  ((NumOpContext)_localctx).eqOp.v; 
				}
				break;
			case GT:
				enterOuterAlt(_localctx, 2);
				{
				setState(428);
				match(GT);
				 ((NumOpContext)_localctx).v =  BinaryOperator.MoreThan; 
				}
				break;
			case GEQ:
				enterOuterAlt(_localctx, 3);
				{
				setState(430);
				match(GEQ);
				 ((NumOpContext)_localctx).v =  BinaryOperator.MoreEqual; 
				}
				break;
			case LT:
				enterOuterAlt(_localctx, 4);
				{
				setState(432);
				match(LT);
				 ((NumOpContext)_localctx).v =  BinaryOperator.LessThan; 
				}
				break;
			case LEQ:
				enterOuterAlt(_localctx, 5);
				{
				setState(434);
				match(LEQ);
				 ((NumOpContext)_localctx).v =  BinaryOperator.LessEqual; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 10:
			return nodeAttributes_sempred((NodeAttributesContext)_localctx, predIndex);
		case 12:
			return relatConst_sempred((RelatConstContext)_localctx, predIndex);
		case 15:
			return graphExpr_sempred((GraphExprContext)_localctx, predIndex);
		case 23:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean nodeAttributes_sempred(NodeAttributesContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean relatConst_sempred(RelatConstContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean graphExpr_sempred(GraphExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 4);
		case 3:
			return precpred(_ctx, 3);
		case 4:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 5:
			return precpred(_ctx, 4);
		case 6:
			return precpred(_ctx, 3);
		case 7:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3T\u01b9\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \3\2"+
		"\3\2\3\2\3\2\3\2\7\2F\n\2\f\2\16\2I\13\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4\\\n\4\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\5\n\u0080\n\n\3\13\3\13\3"+
		"\13\3\13\3\13\3\13\3\13\3\13\5\13\u008a\n\13\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\7\f\u0096\n\f\f\f\16\f\u0099\13\f\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\5\r\u00b2\n\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\7\16\u00bd"+
		"\n\16\f\16\16\16\u00c0\13\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\5\17\u00cb\n\17\3\17\3\17\3\17\5\17\u00d0\n\17\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\5\20\u00d9\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\5\21\u00e6\n\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\7\21\u00f7\n\21\f\21\16\21\u00fa\13"+
		"\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\22\3\22\3\22\5\22\u010d\n\22\3\23\3\23\3\23\3\23\5\23\u0113\n\23"+
		"\3\24\3\24\3\24\3\24\3\24\5\24\u011a\n\24\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\5\25\u0131\n\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\5\26\u0149"+
		"\n\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u0162\n\27\3\30"+
		"\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31"+
		"\3\31\3\31\5\31\u0175\n\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\7\31\u0186\n\31\f\31\16\31\u0189\13\31"+
		"\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\5\32\u019a\n\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37"+
		"\3\37\3\37\3\37\3\37\5\37\u01aa\n\37\3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 "+
		"\5 \u01b7\n \3 \2\6\26\32 \60!\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36"+
		" \"$&(*,.\60\62\64\668:<>\2\6\5\2(+--\60\63\5\2,,./\649\3\2:<\3\2=?\2"+
		"\u01c9\2G\3\2\2\2\4J\3\2\2\2\6[\3\2\2\2\b]\3\2\2\2\nd\3\2\2\2\fm\3\2\2"+
		"\2\16r\3\2\2\2\20y\3\2\2\2\22\177\3\2\2\2\24\u0089\3\2\2\2\26\u008b\3"+
		"\2\2\2\30\u00b1\3\2\2\2\32\u00b3\3\2\2\2\34\u00cf\3\2\2\2\36\u00d8\3\2"+
		"\2\2 \u00e5\3\2\2\2\"\u010c\3\2\2\2$\u0112\3\2\2\2&\u0119\3\2\2\2(\u0130"+
		"\3\2\2\2*\u0148\3\2\2\2,\u0161\3\2\2\2.\u0163\3\2\2\2\60\u0174\3\2\2\2"+
		"\62\u0199\3\2\2\2\64\u019b\3\2\2\2\66\u019d\3\2\2\28\u019f\3\2\2\2:\u01a1"+
		"\3\2\2\2<\u01a9\3\2\2\2>\u01b6\3\2\2\2@F\5\4\3\2AF\5\6\4\2BF\5\n\6\2C"+
		"F\5\f\7\2DF\5\16\b\2E@\3\2\2\2EA\3\2\2\2EB\3\2\2\2EC\3\2\2\2ED\3\2\2\2"+
		"FI\3\2\2\2GE\3\2\2\2GH\3\2\2\2H\3\3\2\2\2IG\3\2\2\2JK\7\33\2\2KL\7O\2"+
		"\2LM\7\3\2\2MN\b\3\1\2N\5\3\2\2\2OP\7P\2\2PQ\7B\2\2QR\5 \21\2RS\7\3\2"+
		"\2ST\b\4\1\2T\\\3\2\2\2UV\7P\2\2VW\7B\2\2WX\5\b\5\2XY\7\3\2\2YZ\b\4\1"+
		"\2Z\\\3\2\2\2[O\3\2\2\2[U\3\2\2\2\\\7\3\2\2\2]^\7\25\2\2^_\7\20\2\2_`"+
		"\5\36\20\2`a\5\20\t\2ab\5\22\n\2bc\b\5\1\2c\t\3\2\2\2de\7\25\2\2ef\7\20"+
		"\2\2fg\5\36\20\2gh\5\20\t\2hi\5\22\n\2ij\5\24\13\2jk\7\3\2\2kl\b\6\1\2"+
		"l\13\3\2\2\2mn\7\27\2\2no\5 \21\2op\7\3\2\2pq\b\7\1\2q\r\3\2\2\2rs\7\30"+
		"\2\2st\5 \21\2tu\7\26\2\2uv\7O\2\2vw\7\3\2\2wx\b\b\1\2x\17\3\2\2\2yz\7"+
		"\35\2\2z{\5\26\f\2{\21\3\2\2\2|}\7\21\2\2}\u0080\5\32\16\2~\u0080\3\2"+
		"\2\2\177|\3\2\2\2\177~\3\2\2\2\u0080\23\3\2\2\2\u0081\u0082\7\31\2\2\u0082"+
		"\u0083\7\5\2\2\u0083\u008a\b\13\1\2\u0084\u0085\7\31\2\2\u0085\u0086\7"+
		"\5\2\2\u0086\u0087\7\26\2\2\u0087\u0088\7P\2\2\u0088\u008a\b\13\1\2\u0089"+
		"\u0081\3\2\2\2\u0089\u0084\3\2\2\2\u008a\25\3\2\2\2\u008b\u008c\b\f\1"+
		"\2\u008c\u008d\7P\2\2\u008d\u008e\7\b\2\2\u008e\u008f\5\60\31\2\u008f"+
		"\u0090\7\t\2\2\u0090\u0091\b\f\1\2\u0091\u0097\3\2\2\2\u0092\u0093\f\4"+
		"\2\2\u0093\u0094\7\4\2\2\u0094\u0096\5\26\f\5\u0095\u0092\3\2\2\2\u0096"+
		"\u0099\3\2\2\2\u0097\u0095\3\2\2\2\u0097\u0098\3\2\2\2\u0098\27\3\2\2"+
		"\2\u0099\u0097\3\2\2\2\u009a\u009b\7L\2\2\u009b\u009c\7\n\2\2\u009c\u009d"+
		"\7F\2\2\u009d\u009e\7N\2\2\u009e\u009f\7&\2\2\u009f\u00a0\7\13\2\2\u00a0"+
		"\u00b2\b\r\1\2\u00a1\u00a2\7L\2\2\u00a2\u00a3\7\n\2\2\u00a3\u00a4\7F\2"+
		"\2\u00a4\u00a5\7N\2\2\u00a5\u00a6\7\'\2\2\u00a6\u00a7\7\13\2\2\u00a7\u00b2"+
		"\b\r\1\2\u00a8\u00a9\7L\2\2\u00a9\u00aa\7\n\2\2\u00aa\u00ab\7F\2\2\u00ab"+
		"\u00ac\7N\2\2\u00ac\u00ad\7%\2\2\u00ad\u00ae\7\13\2\2\u00ae\u00b2\b\r"+
		"\1\2\u00af\u00b0\7M\2\2\u00b0\u00b2\b\r\1\2\u00b1\u009a\3\2\2\2\u00b1"+
		"\u00a1\3\2\2\2\u00b1\u00a8\3\2\2\2\u00b1\u00af\3\2\2\2\u00b2\31\3\2\2"+
		"\2\u00b3\u00b4\b\16\1\2\u00b4\u00b5\5\34\17\2\u00b5\u00b6\b\16\1\2\u00b6"+
		"\u00be\3\2\2\2\u00b7\u00b8\f\4\2\2\u00b8\u00b9\5\30\r\2\u00b9\u00ba\5"+
		"\32\16\5\u00ba\u00bb\b\16\1\2\u00bb\u00bd\3\2\2\2\u00bc\u00b7\3\2\2\2"+
		"\u00bd\u00c0\3\2\2\2\u00be\u00bc\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf\33"+
		"\3\2\2\2\u00c0\u00be\3\2\2\2\u00c1\u00c2\7P\2\2\u00c2\u00c3\7A\2\2\u00c3"+
		"\u00c4\7P\2\2\u00c4\u00d0\b\17\1\2\u00c5\u00ca\7P\2\2\u00c6\u00c7\7\n"+
		"\2\2\u00c7\u00c8\5:\36\2\u00c8\u00c9\7\13\2\2\u00c9\u00cb\3\2\2\2\u00ca"+
		"\u00c6\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb\u00cc\3\2\2\2\u00cc\u00cd\7A"+
		"\2\2\u00cd\u00ce\7P\2\2\u00ce\u00d0\b\17\1\2\u00cf\u00c1\3\2\2\2\u00cf"+
		"\u00c5\3\2\2\2\u00d0\35\3\2\2\2\u00d1\u00d2\7\34\2\2\u00d2\u00d3\7\6\2"+
		"\2\u00d3\u00d4\7P\2\2\u00d4\u00d5\7\7\2\2\u00d5\u00d9\b\20\1\2\u00d6\u00d7"+
		"\7P\2\2\u00d7\u00d9\b\20\1\2\u00d8\u00d1\3\2\2\2\u00d8\u00d6\3\2\2\2\u00d9"+
		"\37\3\2\2\2\u00da\u00db\b\21\1\2\u00db\u00dc\7\6\2\2\u00dc\u00dd\5 \21"+
		"\2\u00dd\u00de\7\7\2\2\u00de\u00df\b\21\1\2\u00df\u00e6\3\2\2\2\u00e0"+
		"\u00e1\7P\2\2\u00e1\u00e6\b\21\1\2\u00e2\u00e3\5\"\22\2\u00e3\u00e4\b"+
		"\21\1\2\u00e4\u00e6\3\2\2\2\u00e5\u00da\3\2\2\2\u00e5\u00e0\3\2\2\2\u00e5"+
		"\u00e2\3\2\2\2\u00e6\u00f8\3\2\2\2\u00e7\u00e8\f\6\2\2\u00e8\u00e9\7I"+
		"\2\2\u00e9\u00ea\5 \21\7\u00ea\u00eb\b\21\1\2\u00eb\u00f7\3\2\2\2\u00ec"+
		"\u00ed\f\5\2\2\u00ed\u00ee\7J\2\2\u00ee\u00ef\5 \21\6\u00ef\u00f0\b\21"+
		"\1\2\u00f0\u00f7\3\2\2\2\u00f1\u00f2\f\4\2\2\u00f2\u00f3\7K\2\2\u00f3"+
		"\u00f4\5 \21\5\u00f4\u00f5\b\21\1\2\u00f5\u00f7\3\2\2\2\u00f6\u00e7\3"+
		"\2\2\2\u00f6\u00ec\3\2\2\2\u00f6\u00f1\3\2\2\2\u00f7\u00fa\3\2\2\2\u00f8"+
		"\u00f6\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9!\3\2\2\2\u00fa\u00f8\3\2\2\2"+
		"\u00fb\u00fc\5$\23\2\u00fc\u00fd\7\24\2\2\u00fd\u00fe\5&\24\2\u00fe\u00ff"+
		"\7\20\2\2\u00ff\u0100\5\36\20\2\u0100\u0101\b\22\1\2\u0101\u0102\5(\25"+
		"\2\u0102\u0103\5,\27\2\u0103\u0104\b\22\1\2\u0104\u010d\3\2\2\2\u0105"+
		"\u0106\7\25\2\2\u0106\u0107\7\20\2\2\u0107\u0108\5 \21\2\u0108\u0109\b"+
		"\22\1\2\u0109\u010a\5(\25\2\u010a\u010b\b\22\1\2\u010b\u010d\3\2\2\2\u010c"+
		"\u00fb\3\2\2\2\u010c\u0105\3\2\2\2\u010d#\3\2\2\2\u010e\u010f\7\22\2\2"+
		"\u010f\u0113\b\23\1\2\u0110\u0111\7\23\2\2\u0111\u0113\b\23\1\2\u0112"+
		"\u010e\3\2\2\2\u0112\u0110\3\2\2\2\u0113%\3\2\2\2\u0114\u0115\7P\2\2\u0115"+
		"\u011a\b\24\1\2\u0116\u0117\5.\30\2\u0117\u0118\b\24\1\2\u0118\u011a\3"+
		"\2\2\2\u0119\u0114\3\2\2\2\u0119\u0116\3\2\2\2\u011a\'\3\2\2\2\u011b\u011c"+
		"\7\36\2\2\u011c\u011d\5*\26\2\u011d\u011e\b\25\1\2\u011e\u0131\3\2\2\2"+
		"\u011f\u0120\7\37\2\2\u0120\u0121\5*\26\2\u0121\u0122\b\25\1\2\u0122\u0131"+
		"\3\2\2\2\u0123\u0124\7\36\2\2\u0124\u0125\5*\26\2\u0125\u0126\7\37\2\2"+
		"\u0126\u0127\5*\26\2\u0127\u0128\b\25\1\2\u0128\u0131\3\2\2\2\u0129\u012a"+
		"\7\37\2\2\u012a\u012b\5*\26\2\u012b\u012c\7\36\2\2\u012c\u012d\5*\26\2"+
		"\u012d\u012e\b\25\1\2\u012e\u0131\3\2\2\2\u012f\u0131\3\2\2\2\u0130\u011b"+
		"\3\2\2\2\u0130\u011f\3\2\2\2\u0130\u0123\3\2\2\2\u0130\u0129\3\2\2\2\u0130"+
		"\u012f\3\2\2\2\u0131)\3\2\2\2\u0132\u0133\7 \2\2\u0133\u0134\5.\30\2\u0134"+
		"\u0135\b\26\1\2\u0135\u0149\3\2\2\2\u0136\u0137\7!\2\2\u0137\u0138\5."+
		"\30\2\u0138\u0139\b\26\1\2\u0139\u0149\3\2\2\2\u013a\u013b\7 \2\2\u013b"+
		"\u013c\5.\30\2\u013c\u013d\7\4\2\2\u013d\u013e\7!\2\2\u013e\u013f\5.\30"+
		"\2\u013f\u0140\b\26\1\2\u0140\u0149\3\2\2\2\u0141\u0142\7!\2\2\u0142\u0143"+
		"\5.\30\2\u0143\u0144\7\4\2\2\u0144\u0145\7 \2\2\u0145\u0146\5.\30\2\u0146"+
		"\u0147\b\26\1\2\u0147\u0149\3\2\2\2\u0148\u0132\3\2\2\2\u0148\u0136\3"+
		"\2\2\2\u0148\u013a\3\2\2\2\u0148\u0141\3\2\2\2\u0149+\3\2\2\2\u014a\u014b"+
		"\7\"\2\2\u014b\u014c\7$\2\2\u014c\u014d\7N\2\2\u014d\u0162\b\27\1\2\u014e"+
		"\u014f\7\"\2\2\u014f\u0150\7#\2\2\u0150\u0151\7N\2\2\u0151\u0162\b\27"+
		"\1\2\u0152\u0153\7\"\2\2\u0153\u0154\7$\2\2\u0154\u0155\7N\2\2\u0155\u0156"+
		"\7\4\2\2\u0156\u0157\7#\2\2\u0157\u0158\7N\2\2\u0158\u0162\b\27\1\2\u0159"+
		"\u015a\7\"\2\2\u015a\u015b\7#\2\2\u015b\u015c\7N\2\2\u015c\u015d\7\4\2"+
		"\2\u015d\u015e\7$\2\2\u015e\u015f\7N\2\2\u015f\u0162\b\27\1\2\u0160\u0162"+
		"\3\2\2\2\u0161\u014a\3\2\2\2\u0161\u014e\3\2\2\2\u0161\u0152\3\2\2\2\u0161"+
		"\u0159\3\2\2\2\u0161\u0160\3\2\2\2\u0162-\3\2\2\2\u0163\u0164\7\35\2\2"+
		"\u0164\u0165\5\60\31\2\u0165\u0166\b\30\1\2\u0166/\3\2\2\2\u0167\u0168"+
		"\b\31\1\2\u0168\u0169\7\6\2\2\u0169\u016a\5\60\31\2\u016a\u016b\7\7\2"+
		"\2\u016b\u016c\b\31\1\2\u016c\u0175\3\2\2\2\u016d\u016e\7\16\2\2\u016e"+
		"\u016f\5\60\31\7\u016f\u0170\b\31\1\2\u0170\u0175\3\2\2\2\u0171\u0172"+
		"\5\62\32\2\u0172\u0173\b\31\1\2\u0173\u0175\3\2\2\2\u0174\u0167\3\2\2"+
		"\2\u0174\u016d\3\2\2\2\u0174\u0171\3\2\2\2\u0175\u0187\3\2\2\2\u0176\u0177"+
		"\f\6\2\2\u0177\u0178\7\f\2\2\u0178\u0179\5\60\31\7\u0179\u017a\b\31\1"+
		"\2\u017a\u0186\3\2\2\2\u017b\u017c\f\5\2\2\u017c\u017d\7\r\2\2\u017d\u017e"+
		"\5\60\31\6\u017e\u017f\b\31\1\2\u017f\u0186\3\2\2\2\u0180\u0181\f\4\2"+
		"\2\u0181\u0182\7\4\2\2\u0182\u0183\5\60\31\5\u0183\u0184\b\31\1\2\u0184"+
		"\u0186\3\2\2\2\u0185\u0176\3\2\2\2\u0185\u017b\3\2\2\2\u0185\u0180\3\2"+
		"\2\2\u0186\u0189\3\2\2\2\u0187\u0185\3\2\2\2\u0187\u0188\3\2\2\2\u0188"+
		"\61\3\2\2\2\u0189\u0187\3\2\2\2\u018a\u018b\5\64\33\2\u018b\u018c\5<\37"+
		"\2\u018c\u018d\58\35\2\u018d\u018e\b\32\1\2\u018e\u019a\3\2\2\2\u018f"+
		"\u0190\5\64\33\2\u0190\u0191\5<\37\2\u0191\u0192\7O\2\2\u0192\u0193\b"+
		"\32\1\2\u0193\u019a\3\2\2\2\u0194\u0195\5\66\34\2\u0195\u0196\5> \2\u0196"+
		"\u0197\7N\2\2\u0197\u0198\b\32\1\2\u0198\u019a\3\2\2\2\u0199\u018a\3\2"+
		"\2\2\u0199\u018f\3\2\2\2\u0199\u0194\3\2\2\2\u019a\63\3\2\2\2\u019b\u019c"+
		"\t\2\2\2\u019c\65\3\2\2\2\u019d\u019e\t\3\2\2\u019e\67\3\2\2\2\u019f\u01a0"+
		"\t\4\2\2\u01a09\3\2\2\2\u01a1\u01a2\t\5\2\2\u01a2;\3\2\2\2\u01a3\u01a4"+
		"\7B\2\2\u01a4\u01aa\b\37\1\2\u01a5\u01a6\7C\2\2\u01a6\u01aa\b\37\1\2\u01a7"+
		"\u01a8\7H\2\2\u01a8\u01aa\b\37\1\2\u01a9\u01a3\3\2\2\2\u01a9\u01a5\3\2"+
		"\2\2\u01a9\u01a7\3\2\2\2\u01aa=\3\2\2\2\u01ab\u01ac\5<\37\2\u01ac\u01ad"+
		"\b \1\2\u01ad\u01b7\3\2\2\2\u01ae\u01af\7D\2\2\u01af\u01b7\b \1\2\u01b0"+
		"\u01b1\7E\2\2\u01b1\u01b7\b \1\2\u01b2\u01b3\7F\2\2\u01b3\u01b7\b \1\2"+
		"\u01b4\u01b5\7G\2\2\u01b5\u01b7\b \1\2\u01b6\u01ab\3\2\2\2\u01b6\u01ae"+
		"\3\2\2\2\u01b6\u01b0\3\2\2\2\u01b6\u01b2\3\2\2\2\u01b6\u01b4\3\2\2\2\u01b7"+
		"?\3\2\2\2\34EG[\177\u0089\u0097\u00b1\u00be\u00ca\u00cf\u00d8\u00e5\u00f6"+
		"\u00f8\u010c\u0112\u0119\u0130\u0148\u0161\u0174\u0185\u0187\u0199\u01a9"+
		"\u01b6";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}