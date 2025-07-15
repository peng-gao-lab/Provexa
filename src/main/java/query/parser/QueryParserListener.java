// Generated from QueryParser.g4 by ANTLR 4.9.2

    package query.parser;
    import java.math.BigDecimal;
    import query.backtracking.*;
    import query.search.*;
    import executor.*;
    import java.util.*;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link QueryParser}.
 */
public interface QueryParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link QueryParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(QueryParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(QueryParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#connectStmt}.
	 * @param ctx the parse tree
	 */
	void enterConnectStmt(QueryParser.ConnectStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#connectStmt}.
	 * @param ctx the parse tree
	 */
	void exitConnectStmt(QueryParser.ConnectStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#assignStmt}.
	 * @param ctx the parse tree
	 */
	void enterAssignStmt(QueryParser.AssignStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#assignStmt}.
	 * @param ctx the parse tree
	 */
	void exitAssignStmt(QueryParser.AssignStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#searchExpr}.
	 * @param ctx the parse tree
	 */
	void enterSearchExpr(QueryParser.SearchExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#searchExpr}.
	 * @param ctx the parse tree
	 */
	void exitSearchExpr(QueryParser.SearchExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#searchStmt}.
	 * @param ctx the parse tree
	 */
	void enterSearchStmt(QueryParser.SearchStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#searchStmt}.
	 * @param ctx the parse tree
	 */
	void exitSearchStmt(QueryParser.SearchStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#displayStmt}.
	 * @param ctx the parse tree
	 */
	void enterDisplayStmt(QueryParser.DisplayStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#displayStmt}.
	 * @param ctx the parse tree
	 */
	void exitDisplayStmt(QueryParser.DisplayStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#exportStmt}.
	 * @param ctx the parse tree
	 */
	void enterExportStmt(QueryParser.ExportStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#exportStmt}.
	 * @param ctx the parse tree
	 */
	void exitExportStmt(QueryParser.ExportStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#nodeMatchTerm}.
	 * @param ctx the parse tree
	 */
	void enterNodeMatchTerm(QueryParser.NodeMatchTermContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#nodeMatchTerm}.
	 * @param ctx the parse tree
	 */
	void exitNodeMatchTerm(QueryParser.NodeMatchTermContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#relatTerm}.
	 * @param ctx the parse tree
	 */
	void enterRelatTerm(QueryParser.RelatTermContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#relatTerm}.
	 * @param ctx the parse tree
	 */
	void exitRelatTerm(QueryParser.RelatTermContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#returnTerm}.
	 * @param ctx the parse tree
	 */
	void enterReturnTerm(QueryParser.ReturnTermContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#returnTerm}.
	 * @param ctx the parse tree
	 */
	void exitReturnTerm(QueryParser.ReturnTermContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#nodeAttributes}.
	 * @param ctx the parse tree
	 */
	void enterNodeAttributes(QueryParser.NodeAttributesContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#nodeAttributes}.
	 * @param ctx the parse tree
	 */
	void exitNodeAttributes(QueryParser.NodeAttributesContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#logicOpt}.
	 * @param ctx the parse tree
	 */
	void enterLogicOpt(QueryParser.LogicOptContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#logicOpt}.
	 * @param ctx the parse tree
	 */
	void exitLogicOpt(QueryParser.LogicOptContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#relatConst}.
	 * @param ctx the parse tree
	 */
	void enterRelatConst(QueryParser.RelatConstContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#relatConst}.
	 * @param ctx the parse tree
	 */
	void exitRelatConst(QueryParser.RelatConstContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#relatExpr}.
	 * @param ctx the parse tree
	 */
	void enterRelatExpr(QueryParser.RelatExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#relatExpr}.
	 * @param ctx the parse tree
	 */
	void exitRelatExpr(QueryParser.RelatExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#dataSource}.
	 * @param ctx the parse tree
	 */
	void enterDataSource(QueryParser.DataSourceContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#dataSource}.
	 * @param ctx the parse tree
	 */
	void exitDataSource(QueryParser.DataSourceContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#graphExpr}.
	 * @param ctx the parse tree
	 */
	void enterGraphExpr(QueryParser.GraphExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#graphExpr}.
	 * @param ctx the parse tree
	 */
	void exitGraphExpr(QueryParser.GraphExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#trackExpr}.
	 * @param ctx the parse tree
	 */
	void enterTrackExpr(QueryParser.TrackExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#trackExpr}.
	 * @param ctx the parse tree
	 */
	void exitTrackExpr(QueryParser.TrackExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#direction}.
	 * @param ctx the parse tree
	 */
	void enterDirection(QueryParser.DirectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#direction}.
	 * @param ctx the parse tree
	 */
	void exitDirection(QueryParser.DirectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#startTerm}.
	 * @param ctx the parse tree
	 */
	void enterStartTerm(QueryParser.StartTermContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#startTerm}.
	 * @param ctx the parse tree
	 */
	void exitStartTerm(QueryParser.StartTermContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#filterTerm}.
	 * @param ctx the parse tree
	 */
	void enterFilterTerm(QueryParser.FilterTermContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#filterTerm}.
	 * @param ctx the parse tree
	 */
	void exitFilterTerm(QueryParser.FilterTermContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#typeFilterTerm}.
	 * @param ctx the parse tree
	 */
	void enterTypeFilterTerm(QueryParser.TypeFilterTermContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#typeFilterTerm}.
	 * @param ctx the parse tree
	 */
	void exitTypeFilterTerm(QueryParser.TypeFilterTermContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#limitTerm}.
	 * @param ctx the parse tree
	 */
	void enterLimitTerm(QueryParser.LimitTermContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#limitTerm}.
	 * @param ctx the parse tree
	 */
	void exitLimitTerm(QueryParser.LimitTermContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#constraintExpr}.
	 * @param ctx the parse tree
	 */
	void enterConstraintExpr(QueryParser.ConstraintExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#constraintExpr}.
	 * @param ctx the parse tree
	 */
	void exitConstraintExpr(QueryParser.ConstraintExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(QueryParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(QueryParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#binaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpr(QueryParser.BinaryExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#binaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpr(QueryParser.BinaryExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#strId}.
	 * @param ctx the parse tree
	 */
	void enterStrId(QueryParser.StrIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#strId}.
	 * @param ctx the parse tree
	 */
	void exitStrId(QueryParser.StrIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#numId}.
	 * @param ctx the parse tree
	 */
	void enterNumId(QueryParser.NumIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#numId}.
	 * @param ctx the parse tree
	 */
	void exitNumId(QueryParser.NumIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#entityType}.
	 * @param ctx the parse tree
	 */
	void enterEntityType(QueryParser.EntityTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#entityType}.
	 * @param ctx the parse tree
	 */
	void exitEntityType(QueryParser.EntityTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#optType}.
	 * @param ctx the parse tree
	 */
	void enterOptType(QueryParser.OptTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#optType}.
	 * @param ctx the parse tree
	 */
	void exitOptType(QueryParser.OptTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#eqOp}.
	 * @param ctx the parse tree
	 */
	void enterEqOp(QueryParser.EqOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#eqOp}.
	 * @param ctx the parse tree
	 */
	void exitEqOp(QueryParser.EqOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link QueryParser#numOp}.
	 * @param ctx the parse tree
	 */
	void enterNumOp(QueryParser.NumOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link QueryParser#numOp}.
	 * @param ctx the parse tree
	 */
	void exitNumOp(QueryParser.NumOpContext ctx);
}