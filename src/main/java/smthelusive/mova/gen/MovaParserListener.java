package smthelusive.mova.gen;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MovaParser}.
 */
public interface MovaParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MovaParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(MovaParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link MovaParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(MovaParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link MovaParser#action}.
	 * @param ctx the parse tree
	 */
	void enterExpression(MovaParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MovaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(MovaParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MovaParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(MovaParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link MovaParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(MovaParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link MovaParser#output}.
	 * @param ctx the parse tree
	 */
	void enterOutput(MovaParser.OutputContext ctx);
	/**
	 * Exit a parse tree produced by {@link MovaParser#output}.
	 * @param ctx the parse tree
	 */
	void exitOutput(MovaParser.OutputContext ctx);
	/**
	 * Enter a parse tree produced by {@link MovaParser#decrement}.
	 * @param ctx the parse tree
	 */
	void enterDecrement(MovaParser.DecrementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MovaParser#decrement}.
	 * @param ctx the parse tree
	 */
	void exitDecrement(MovaParser.DecrementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MovaParser#increment}.
	 * @param ctx the parse tree
	 */
	void enterIncrement(MovaParser.IncrementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MovaParser#increment}.
	 * @param ctx the parse tree
	 */
	void exitIncrement(MovaParser.IncrementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MovaParser#slavaUkraini}.
	 * @param ctx the parse tree
	 */
	void enterSlavaUkraini(MovaParser.SlavaUkrainiContext ctx);
	/**
	 * Exit a parse tree produced by {@link MovaParser#slavaUkraini}.
	 * @param ctx the parse tree
	 */
	void exitSlavaUkraini(MovaParser.SlavaUkrainiContext ctx);
	/**
	 * Enter a parse tree produced by {@link MovaParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommand(MovaParser.CommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link MovaParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommand(MovaParser.CommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link MovaParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(MovaParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MovaParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(MovaParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MovaParser#conditional}.
	 * @param ctx the parse tree
	 */
	void enterConditional(MovaParser.ConditionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link MovaParser#conditional}.
	 * @param ctx the parse tree
	 */
	void exitConditional(MovaParser.ConditionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link MovaParser#loop}.
	 * @param ctx the parse tree
	 */
	void enterLoop(MovaParser.LoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link MovaParser#loop}.
	 * @param ctx the parse tree
	 */
	void exitLoop(MovaParser.LoopContext ctx);
	/**
	 * Enter a parse tree produced by {@link MovaParser#validStructure}.
	 * @param ctx the parse tree
	 */
	void enterValidStructure(MovaParser.ValidStructureContext ctx);
	/**
	 * Exit a parse tree produced by {@link MovaParser#validStructure}.
	 * @param ctx the parse tree
	 */
	void exitValidStructure(MovaParser.ValidStructureContext ctx);
	/**
	 * Enter a parse tree produced by {@link MovaParser#validProgram}.
	 * @param ctx the parse tree
	 */
	void enterValidProgram(MovaParser.ValidProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MovaParser#validProgram}.
	 * @param ctx the parse tree
	 */
	void exitValidProgram(MovaParser.ValidProgramContext ctx);
}