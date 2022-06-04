package smthelusive.mova.gen;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MovaParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MovaParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MovaParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(MovaParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link MovaParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(MovaParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MovaParser#decrement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecrement(MovaParser.DecrementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MovaParser#increment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIncrement(MovaParser.IncrementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MovaParser#allKindsExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAllKindsExpression(MovaParser.AllKindsExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MovaParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(MovaParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link MovaParser#output}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutput(MovaParser.OutputContext ctx);
	/**
	 * Visit a parse tree produced by {@link MovaParser#slavaUkraini}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSlavaUkraini(MovaParser.SlavaUkrainiContext ctx);
	/**
	 * Visit a parse tree produced by {@link MovaParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand(MovaParser.CommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link MovaParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(MovaParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MovaParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(MovaParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MovaParser#conditional}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditional(MovaParser.ConditionalContext ctx);
	/**
	 * Visit a parse tree produced by {@link MovaParser#loop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoop(MovaParser.LoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link MovaParser#validStructure}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValidStructure(MovaParser.ValidStructureContext ctx);
	/**
	 * Visit a parse tree produced by {@link MovaParser#validProgram}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValidProgram(MovaParser.ValidProgramContext ctx);
}