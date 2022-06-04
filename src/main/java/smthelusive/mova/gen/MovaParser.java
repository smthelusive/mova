package smthelusive.mova.gen;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MovaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WS=1, DO=2, UNTIL=3, TIMES=4, REPEAT=5, IF=6, OTHERWISE=7, SHOW=8, PLUS=9, 
		MINUS=10, MULTIPLY=11, DIVIDE=12, PREFIX=13, SUFFIX=14, WITH=15, EQUALS=16, 
		LPAREN=17, RPAREN=18, THEN=19, COLON=20, OR=21, AND=22, NOT=23, INCREMENT=24, 
		DECREMENT=25, COMMA=26, ALSO=27, DOT=28, MORETHAN=29, LESSTHAN=30, MOREOREQUAL=31, 
		LESSOREQUAL=32, NOTEQUAL=33, INTEGER=34, DECIMAL=35, STRING=36, IDENTIFIER=37, 
		COMMENT=38, SLAVAUKRAINI=39;
	public static final int
		RULE_value = 0, RULE_expression = 1, RULE_decrement = 2, RULE_increment = 3, 
		RULE_allKindsExpression = 4, RULE_assignment = 5, RULE_output = 6, RULE_slavaUkraini = 7, 
		RULE_command = 8, RULE_block = 9, RULE_condition = 10, RULE_conditional = 11, 
		RULE_loop = 12, RULE_validStructure = 13, RULE_validProgram = 14;
	private static String[] makeRuleNames() {
		return new String[] {
			"value", "expression", "decrement", "increment", "allKindsExpression", 
			"assignment", "output", "slavaUkraini", "command", "block", "condition", 
			"conditional", "loop", "validStructure", "validProgram"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, "'do'", "'until'", "'times'", "'repeat'", "'if'", "'otherwise'", 
			"'show'", null, null, null, null, null, null, "'with'", null, "'('", 
			"')'", "'then'", "':'", null, null, null, null, null, "','", null, "'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WS", "DO", "UNTIL", "TIMES", "REPEAT", "IF", "OTHERWISE", "SHOW", 
			"PLUS", "MINUS", "MULTIPLY", "DIVIDE", "PREFIX", "SUFFIX", "WITH", "EQUALS", 
			"LPAREN", "RPAREN", "THEN", "COLON", "OR", "AND", "NOT", "INCREMENT", 
			"DECREMENT", "COMMA", "ALSO", "DOT", "MORETHAN", "LESSTHAN", "MOREOREQUAL", 
			"LESSOREQUAL", "NOTEQUAL", "INTEGER", "DECIMAL", "STRING", "IDENTIFIER", 
			"COMMENT", "SLAVAUKRAINI"
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
	public String getGrammarFileName() { return "MovaParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MovaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ValueContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(MovaParser.IDENTIFIER, 0); }
		public TerminalNode INTEGER() { return getToken(MovaParser.INTEGER, 0); }
		public TerminalNode DECIMAL() { return getToken(MovaParser.DECIMAL, 0); }
		public TerminalNode STRING() { return getToken(MovaParser.STRING, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MovaParserVisitor ) return ((MovaParserVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << INTEGER) | (1L << DECIMAL) | (1L << STRING) | (1L << IDENTIFIER))) != 0)) ) {
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

	public static class ExpressionContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(MovaParser.LPAREN, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(MovaParser.RPAREN, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode MULTIPLY() { return getToken(MovaParser.MULTIPLY, 0); }
		public TerminalNode DIVIDE() { return getToken(MovaParser.DIVIDE, 0); }
		public TerminalNode PLUS() { return getToken(MovaParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(MovaParser.MINUS, 0); }
		public TerminalNode PREFIX() { return getToken(MovaParser.PREFIX, 0); }
		public TerminalNode SUFFIX() { return getToken(MovaParser.SUFFIX, 0); }
		public TerminalNode WITH() { return getToken(MovaParser.WITH, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MovaParserVisitor ) return ((MovaParserVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 2;
		enterRecursionRule(_localctx, 2, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(38);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				{
				setState(33);
				match(LPAREN);
				setState(34);
				expression(0);
				setState(35);
				match(RPAREN);
				}
				break;
			case INTEGER:
			case DECIMAL:
			case STRING:
			case IDENTIFIER:
				{
				setState(37);
				value();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(51);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(49);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(40);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(41);
						_la = _input.LA(1);
						if ( !(_la==MULTIPLY || _la==DIVIDE) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(42);
						expression(6);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(43);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(44);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(45);
						expression(5);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(46);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(47);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PREFIX) | (1L << SUFFIX) | (1L << WITH))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(48);
						expression(4);
						}
						break;
					}
					} 
				}
				setState(53);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
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

	public static class DecrementContext extends ParserRuleContext {
		public TerminalNode DECREMENT() { return getToken(MovaParser.DECREMENT, 0); }
		public TerminalNode IDENTIFIER() { return getToken(MovaParser.IDENTIFIER, 0); }
		public DecrementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decrement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MovaParserVisitor ) return ((MovaParserVisitor<? extends T>)visitor).visitDecrement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DecrementContext decrement() throws RecognitionException {
		DecrementContext _localctx = new DecrementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_decrement);
		try {
			setState(58);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DECREMENT:
				enterOuterAlt(_localctx, 1);
				{
				setState(54);
				match(DECREMENT);
				setState(55);
				match(IDENTIFIER);
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(56);
				match(IDENTIFIER);
				setState(57);
				match(DECREMENT);
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

	public static class IncrementContext extends ParserRuleContext {
		public TerminalNode INCREMENT() { return getToken(MovaParser.INCREMENT, 0); }
		public TerminalNode IDENTIFIER() { return getToken(MovaParser.IDENTIFIER, 0); }
		public IncrementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_increment; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MovaParserVisitor ) return ((MovaParserVisitor<? extends T>)visitor).visitIncrement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IncrementContext increment() throws RecognitionException {
		IncrementContext _localctx = new IncrementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_increment);
		try {
			setState(64);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INCREMENT:
				enterOuterAlt(_localctx, 1);
				{
				setState(60);
				match(INCREMENT);
				setState(61);
				match(IDENTIFIER);
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(62);
				match(IDENTIFIER);
				setState(63);
				match(INCREMENT);
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

	public static class AllKindsExpressionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public IncrementContext increment() {
			return getRuleContext(IncrementContext.class,0);
		}
		public DecrementContext decrement() {
			return getRuleContext(DecrementContext.class,0);
		}
		public AllKindsExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_allKindsExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MovaParserVisitor ) return ((MovaParserVisitor<? extends T>)visitor).visitAllKindsExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AllKindsExpressionContext allKindsExpression() throws RecognitionException {
		AllKindsExpressionContext _localctx = new AllKindsExpressionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_allKindsExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				setState(66);
				expression(0);
				}
				break;
			case 2:
				{
				setState(67);
				increment();
				}
				break;
			case 3:
				{
				setState(68);
				decrement();
				}
				break;
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

	public static class AssignmentContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(MovaParser.IDENTIFIER, 0); }
		public TerminalNode EQUALS() { return getToken(MovaParser.EQUALS, 0); }
		public AllKindsExpressionContext allKindsExpression() {
			return getRuleContext(AllKindsExpressionContext.class,0);
		}
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MovaParserVisitor ) return ((MovaParserVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			match(IDENTIFIER);
			setState(72);
			match(EQUALS);
			setState(73);
			allKindsExpression();
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

	public static class OutputContext extends ParserRuleContext {
		public TerminalNode SHOW() { return getToken(MovaParser.SHOW, 0); }
		public AllKindsExpressionContext allKindsExpression() {
			return getRuleContext(AllKindsExpressionContext.class,0);
		}
		public OutputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_output; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MovaParserVisitor ) return ((MovaParserVisitor<? extends T>)visitor).visitOutput(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OutputContext output() throws RecognitionException {
		OutputContext _localctx = new OutputContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_output);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			match(SHOW);
			setState(76);
			allKindsExpression();
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

	public static class SlavaUkrainiContext extends ParserRuleContext {
		public TerminalNode SLAVAUKRAINI() { return getToken(MovaParser.SLAVAUKRAINI, 0); }
		public SlavaUkrainiContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_slavaUkraini; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MovaParserVisitor ) return ((MovaParserVisitor<? extends T>)visitor).visitSlavaUkraini(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SlavaUkrainiContext slavaUkraini() throws RecognitionException {
		SlavaUkrainiContext _localctx = new SlavaUkrainiContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_slavaUkraini);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			match(SLAVAUKRAINI);
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

	public static class CommandContext extends ParserRuleContext {
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public DecrementContext decrement() {
			return getRuleContext(DecrementContext.class,0);
		}
		public IncrementContext increment() {
			return getRuleContext(IncrementContext.class,0);
		}
		public OutputContext output() {
			return getRuleContext(OutputContext.class,0);
		}
		public CommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_command; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MovaParserVisitor ) return ((MovaParserVisitor<? extends T>)visitor).visitCommand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommandContext command() throws RecognitionException {
		CommandContext _localctx = new CommandContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_command);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				{
				setState(80);
				assignment();
				}
				break;
			case 2:
				{
				setState(81);
				decrement();
				}
				break;
			case 3:
				{
				setState(82);
				increment();
				}
				break;
			case 4:
				{
				setState(83);
				output();
				}
				break;
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

	public static class BlockContext extends ParserRuleContext {
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
		}
		public List<TerminalNode> ALSO() { return getTokens(MovaParser.ALSO); }
		public TerminalNode ALSO(int i) {
			return getToken(MovaParser.ALSO, i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MovaParserVisitor ) return ((MovaParserVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_block);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			command();
			setState(91);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(87);
					match(ALSO);
					setState(88);
					command();
					}
					} 
				}
				setState(93);
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
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionContext extends ParserRuleContext {
		public List<AllKindsExpressionContext> allKindsExpression() {
			return getRuleContexts(AllKindsExpressionContext.class);
		}
		public AllKindsExpressionContext allKindsExpression(int i) {
			return getRuleContext(AllKindsExpressionContext.class,i);
		}
		public List<TerminalNode> NOT() { return getTokens(MovaParser.NOT); }
		public TerminalNode NOT(int i) {
			return getToken(MovaParser.NOT, i);
		}
		public List<TerminalNode> EQUALS() { return getTokens(MovaParser.EQUALS); }
		public TerminalNode EQUALS(int i) {
			return getToken(MovaParser.EQUALS, i);
		}
		public List<TerminalNode> MORETHAN() { return getTokens(MovaParser.MORETHAN); }
		public TerminalNode MORETHAN(int i) {
			return getToken(MovaParser.MORETHAN, i);
		}
		public List<TerminalNode> LESSTHAN() { return getTokens(MovaParser.LESSTHAN); }
		public TerminalNode LESSTHAN(int i) {
			return getToken(MovaParser.LESSTHAN, i);
		}
		public List<TerminalNode> MOREOREQUAL() { return getTokens(MovaParser.MOREOREQUAL); }
		public TerminalNode MOREOREQUAL(int i) {
			return getToken(MovaParser.MOREOREQUAL, i);
		}
		public List<TerminalNode> LESSOREQUAL() { return getTokens(MovaParser.LESSOREQUAL); }
		public TerminalNode LESSOREQUAL(int i) {
			return getToken(MovaParser.LESSOREQUAL, i);
		}
		public List<TerminalNode> NOTEQUAL() { return getTokens(MovaParser.NOTEQUAL); }
		public TerminalNode NOTEQUAL(int i) {
			return getToken(MovaParser.NOTEQUAL, i);
		}
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MovaParserVisitor ) return ((MovaParserVisitor<? extends T>)visitor).visitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_condition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			allKindsExpression();
			setState(98);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NOT) {
				{
				{
				setState(95);
				match(NOT);
				}
				}
				setState(100);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(105);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQUALS) | (1L << MORETHAN) | (1L << LESSTHAN) | (1L << MOREOREQUAL) | (1L << LESSOREQUAL) | (1L << NOTEQUAL))) != 0)) {
				{
				{
				setState(101);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQUALS) | (1L << MORETHAN) | (1L << LESSTHAN) | (1L << MOREOREQUAL) | (1L << LESSOREQUAL) | (1L << NOTEQUAL))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(102);
				allKindsExpression();
				}
				}
				setState(107);
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

	public static class ConditionalContext extends ParserRuleContext {
		public List<TerminalNode> IF() { return getTokens(MovaParser.IF); }
		public TerminalNode IF(int i) {
			return getToken(MovaParser.IF, i);
		}
		public List<ConditionContext> condition() {
			return getRuleContexts(ConditionContext.class);
		}
		public ConditionContext condition(int i) {
			return getRuleContext(ConditionContext.class,i);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public List<TerminalNode> OTHERWISE() { return getTokens(MovaParser.OTHERWISE); }
		public TerminalNode OTHERWISE(int i) {
			return getToken(MovaParser.OTHERWISE, i);
		}
		public List<TerminalNode> THEN() { return getTokens(MovaParser.THEN); }
		public TerminalNode THEN(int i) {
			return getToken(MovaParser.THEN, i);
		}
		public List<TerminalNode> COLON() { return getTokens(MovaParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(MovaParser.COLON, i);
		}
		public List<TerminalNode> AND() { return getTokens(MovaParser.AND); }
		public TerminalNode AND(int i) {
			return getToken(MovaParser.AND, i);
		}
		public List<TerminalNode> OR() { return getTokens(MovaParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(MovaParser.OR, i);
		}
		public ConditionalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conditional; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MovaParserVisitor ) return ((MovaParserVisitor<? extends T>)visitor).visitConditional(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionalContext conditional() throws RecognitionException {
		ConditionalContext _localctx = new ConditionalContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_conditional);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(120); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(108);
				match(IF);
				setState(109);
				condition();
				setState(114);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==OR || _la==AND) {
					{
					{
					setState(110);
					_la = _input.LA(1);
					if ( !(_la==OR || _la==AND) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(111);
					condition();
					}
					}
					setState(116);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(117);
				_la = _input.LA(1);
				if ( !(_la==THEN || _la==COLON) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(118);
				block();
				}
				}
				setState(122); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==IF );
			setState(128);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OTHERWISE) {
				{
				{
				setState(124);
				match(OTHERWISE);
				setState(125);
				block();
				}
				}
				setState(130);
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

	public static class LoopContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(MovaParser.COLON, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public AllKindsExpressionContext allKindsExpression() {
			return getRuleContext(AllKindsExpressionContext.class,0);
		}
		public TerminalNode TIMES() { return getToken(MovaParser.TIMES, 0); }
		public TerminalNode DO() { return getToken(MovaParser.DO, 0); }
		public TerminalNode REPEAT() { return getToken(MovaParser.REPEAT, 0); }
		public TerminalNode UNTIL() { return getToken(MovaParser.UNTIL, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public LoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loop; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MovaParserVisitor ) return ((MovaParserVisitor<? extends T>)visitor).visitLoop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LoopContext loop() throws RecognitionException {
		LoopContext _localctx = new LoopContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_loop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				{
				{
				setState(131);
				_la = _input.LA(1);
				if ( !(_la==DO || _la==REPEAT) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(137);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case LPAREN:
				case INCREMENT:
				case DECREMENT:
				case INTEGER:
				case DECIMAL:
				case STRING:
				case IDENTIFIER:
					{
					{
					setState(132);
					allKindsExpression();
					setState(133);
					match(TIMES);
					}
					}
					break;
				case UNTIL:
					{
					{
					setState(135);
					match(UNTIL);
					setState(136);
					condition();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(139);
				match(COLON);
				setState(140);
				block();
				}
				}
				break;
			case 2:
				{
				{
				setState(142);
				_la = _input.LA(1);
				if ( !(_la==DO || _la==REPEAT) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(143);
				block();
				setState(144);
				allKindsExpression();
				setState(145);
				match(TIMES);
				}
				}
				break;
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

	public static class ValidStructureContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(MovaParser.DOT, 0); }
		public CommandContext command() {
			return getRuleContext(CommandContext.class,0);
		}
		public ConditionalContext conditional() {
			return getRuleContext(ConditionalContext.class,0);
		}
		public LoopContext loop() {
			return getRuleContext(LoopContext.class,0);
		}
		public List<TerminalNode> ALSO() { return getTokens(MovaParser.ALSO); }
		public TerminalNode ALSO(int i) {
			return getToken(MovaParser.ALSO, i);
		}
		public List<ValidStructureContext> validStructure() {
			return getRuleContexts(ValidStructureContext.class);
		}
		public ValidStructureContext validStructure(int i) {
			return getRuleContext(ValidStructureContext.class,i);
		}
		public SlavaUkrainiContext slavaUkraini() {
			return getRuleContext(SlavaUkrainiContext.class,0);
		}
		public ValidStructureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_validStructure; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MovaParserVisitor ) return ((MovaParserVisitor<? extends T>)visitor).visitValidStructure(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValidStructureContext validStructure() throws RecognitionException {
		ValidStructureContext _localctx = new ValidStructureContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_validStructure);
		int _la;
		try {
			setState(164);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DO:
			case REPEAT:
			case IF:
			case SHOW:
			case INCREMENT:
			case DECREMENT:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(152);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case SHOW:
				case INCREMENT:
				case DECREMENT:
				case IDENTIFIER:
					{
					setState(149);
					command();
					}
					break;
				case IF:
					{
					setState(150);
					conditional();
					}
					break;
				case DO:
				case REPEAT:
					{
					setState(151);
					loop();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(158);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ALSO) {
					{
					{
					setState(154);
					match(ALSO);
					setState(155);
					validStructure();
					}
					}
					setState(160);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(161);
				match(DOT);
				}
				}
				break;
			case SLAVAUKRAINI:
				enterOuterAlt(_localctx, 2);
				{
				setState(163);
				slavaUkraini();
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

	public static class ValidProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(MovaParser.EOF, 0); }
		public List<ValidStructureContext> validStructure() {
			return getRuleContexts(ValidStructureContext.class);
		}
		public ValidStructureContext validStructure(int i) {
			return getRuleContext(ValidStructureContext.class,i);
		}
		public ValidProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_validProgram; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MovaParserVisitor ) return ((MovaParserVisitor<? extends T>)visitor).visitValidProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValidProgramContext validProgram() throws RecognitionException {
		ValidProgramContext _localctx = new ValidProgramContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_validProgram);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(169);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DO) | (1L << REPEAT) | (1L << IF) | (1L << SHOW) | (1L << INCREMENT) | (1L << DECREMENT) | (1L << IDENTIFIER) | (1L << SLAVAUKRAINI))) != 0)) {
				{
				{
				setState(166);
				validStructure();
				}
				}
				setState(171);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(172);
			match(EOF);
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
		case 1:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 5);
		case 1:
			return precpred(_ctx, 4);
		case 2:
			return precpred(_ctx, 3);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\'\u00af\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0003\u0001\'\b\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001"+
		"2\b\u0001\n\u0001\f\u00015\t\u0001\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0003\u0002;\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0003\u0003A\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0003\u0004F\b\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0003\bU\b\b\u0001\t\u0001\t\u0001\t\u0005\tZ\b\t\n"+
		"\t\f\t]\t\t\u0001\n\u0001\n\u0005\na\b\n\n\n\f\nd\t\n\u0001\n\u0001\n"+
		"\u0005\nh\b\n\n\n\f\nk\t\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0005\u000bq\b\u000b\n\u000b\f\u000bt\t\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0004\u000by\b\u000b\u000b\u000b\f\u000bz\u0001\u000b\u0001"+
		"\u000b\u0005\u000b\u007f\b\u000b\n\u000b\f\u000b\u0082\t\u000b\u0001\f"+
		"\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003\f\u008a\b\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003\f\u0094\b\f\u0001"+
		"\r\u0001\r\u0001\r\u0003\r\u0099\b\r\u0001\r\u0001\r\u0005\r\u009d\b\r"+
		"\n\r\f\r\u00a0\t\r\u0001\r\u0001\r\u0001\r\u0003\r\u00a5\b\r\u0001\u000e"+
		"\u0005\u000e\u00a8\b\u000e\n\u000e\f\u000e\u00ab\t\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0000\u0001\u0002\u000f\u0000\u0002\u0004\u0006\b\n"+
		"\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u0000\b\u0001\u0000"+
		"\"%\u0001\u0000\u000b\f\u0001\u0000\t\n\u0001\u0000\r\u000f\u0002\u0000"+
		"\u0010\u0010\u001d!\u0001\u0000\u0015\u0016\u0001\u0000\u0013\u0014\u0002"+
		"\u0000\u0002\u0002\u0005\u0005\u00b7\u0000\u001e\u0001\u0000\u0000\u0000"+
		"\u0002&\u0001\u0000\u0000\u0000\u0004:\u0001\u0000\u0000\u0000\u0006@"+
		"\u0001\u0000\u0000\u0000\bE\u0001\u0000\u0000\u0000\nG\u0001\u0000\u0000"+
		"\u0000\fK\u0001\u0000\u0000\u0000\u000eN\u0001\u0000\u0000\u0000\u0010"+
		"T\u0001\u0000\u0000\u0000\u0012V\u0001\u0000\u0000\u0000\u0014^\u0001"+
		"\u0000\u0000\u0000\u0016x\u0001\u0000\u0000\u0000\u0018\u0093\u0001\u0000"+
		"\u0000\u0000\u001a\u00a4\u0001\u0000\u0000\u0000\u001c\u00a9\u0001\u0000"+
		"\u0000\u0000\u001e\u001f\u0007\u0000\u0000\u0000\u001f\u0001\u0001\u0000"+
		"\u0000\u0000 !\u0006\u0001\uffff\uffff\u0000!\"\u0005\u0011\u0000\u0000"+
		"\"#\u0003\u0002\u0001\u0000#$\u0005\u0012\u0000\u0000$\'\u0001\u0000\u0000"+
		"\u0000%\'\u0003\u0000\u0000\u0000& \u0001\u0000\u0000\u0000&%\u0001\u0000"+
		"\u0000\u0000\'3\u0001\u0000\u0000\u0000()\n\u0005\u0000\u0000)*\u0007"+
		"\u0001\u0000\u0000*2\u0003\u0002\u0001\u0006+,\n\u0004\u0000\u0000,-\u0007"+
		"\u0002\u0000\u0000-2\u0003\u0002\u0001\u0005./\n\u0003\u0000\u0000/0\u0007"+
		"\u0003\u0000\u000002\u0003\u0002\u0001\u00041(\u0001\u0000\u0000\u0000"+
		"1+\u0001\u0000\u0000\u00001.\u0001\u0000\u0000\u000025\u0001\u0000\u0000"+
		"\u000031\u0001\u0000\u0000\u000034\u0001\u0000\u0000\u00004\u0003\u0001"+
		"\u0000\u0000\u000053\u0001\u0000\u0000\u000067\u0005\u0019\u0000\u0000"+
		"7;\u0005%\u0000\u000089\u0005%\u0000\u00009;\u0005\u0019\u0000\u0000:"+
		"6\u0001\u0000\u0000\u0000:8\u0001\u0000\u0000\u0000;\u0005\u0001\u0000"+
		"\u0000\u0000<=\u0005\u0018\u0000\u0000=A\u0005%\u0000\u0000>?\u0005%\u0000"+
		"\u0000?A\u0005\u0018\u0000\u0000@<\u0001\u0000\u0000\u0000@>\u0001\u0000"+
		"\u0000\u0000A\u0007\u0001\u0000\u0000\u0000BF\u0003\u0002\u0001\u0000"+
		"CF\u0003\u0006\u0003\u0000DF\u0003\u0004\u0002\u0000EB\u0001\u0000\u0000"+
		"\u0000EC\u0001\u0000\u0000\u0000ED\u0001\u0000\u0000\u0000F\t\u0001\u0000"+
		"\u0000\u0000GH\u0005%\u0000\u0000HI\u0005\u0010\u0000\u0000IJ\u0003\b"+
		"\u0004\u0000J\u000b\u0001\u0000\u0000\u0000KL\u0005\b\u0000\u0000LM\u0003"+
		"\b\u0004\u0000M\r\u0001\u0000\u0000\u0000NO\u0005\'\u0000\u0000O\u000f"+
		"\u0001\u0000\u0000\u0000PU\u0003\n\u0005\u0000QU\u0003\u0004\u0002\u0000"+
		"RU\u0003\u0006\u0003\u0000SU\u0003\f\u0006\u0000TP\u0001\u0000\u0000\u0000"+
		"TQ\u0001\u0000\u0000\u0000TR\u0001\u0000\u0000\u0000TS\u0001\u0000\u0000"+
		"\u0000U\u0011\u0001\u0000\u0000\u0000V[\u0003\u0010\b\u0000WX\u0005\u001b"+
		"\u0000\u0000XZ\u0003\u0010\b\u0000YW\u0001\u0000\u0000\u0000Z]\u0001\u0000"+
		"\u0000\u0000[Y\u0001\u0000\u0000\u0000[\\\u0001\u0000\u0000\u0000\\\u0013"+
		"\u0001\u0000\u0000\u0000][\u0001\u0000\u0000\u0000^b\u0003\b\u0004\u0000"+
		"_a\u0005\u0017\u0000\u0000`_\u0001\u0000\u0000\u0000ad\u0001\u0000\u0000"+
		"\u0000b`\u0001\u0000\u0000\u0000bc\u0001\u0000\u0000\u0000ci\u0001\u0000"+
		"\u0000\u0000db\u0001\u0000\u0000\u0000ef\u0007\u0004\u0000\u0000fh\u0003"+
		"\b\u0004\u0000ge\u0001\u0000\u0000\u0000hk\u0001\u0000\u0000\u0000ig\u0001"+
		"\u0000\u0000\u0000ij\u0001\u0000\u0000\u0000j\u0015\u0001\u0000\u0000"+
		"\u0000ki\u0001\u0000\u0000\u0000lm\u0005\u0006\u0000\u0000mr\u0003\u0014"+
		"\n\u0000no\u0007\u0005\u0000\u0000oq\u0003\u0014\n\u0000pn\u0001\u0000"+
		"\u0000\u0000qt\u0001\u0000\u0000\u0000rp\u0001\u0000\u0000\u0000rs\u0001"+
		"\u0000\u0000\u0000su\u0001\u0000\u0000\u0000tr\u0001\u0000\u0000\u0000"+
		"uv\u0007\u0006\u0000\u0000vw\u0003\u0012\t\u0000wy\u0001\u0000\u0000\u0000"+
		"xl\u0001\u0000\u0000\u0000yz\u0001\u0000\u0000\u0000zx\u0001\u0000\u0000"+
		"\u0000z{\u0001\u0000\u0000\u0000{\u0080\u0001\u0000\u0000\u0000|}\u0005"+
		"\u0007\u0000\u0000}\u007f\u0003\u0012\t\u0000~|\u0001\u0000\u0000\u0000"+
		"\u007f\u0082\u0001\u0000\u0000\u0000\u0080~\u0001\u0000\u0000\u0000\u0080"+
		"\u0081\u0001\u0000\u0000\u0000\u0081\u0017\u0001\u0000\u0000\u0000\u0082"+
		"\u0080\u0001\u0000\u0000\u0000\u0083\u0089\u0007\u0007\u0000\u0000\u0084"+
		"\u0085\u0003\b\u0004\u0000\u0085\u0086\u0005\u0004\u0000\u0000\u0086\u008a"+
		"\u0001\u0000\u0000\u0000\u0087\u0088\u0005\u0003\u0000\u0000\u0088\u008a"+
		"\u0003\u0014\n\u0000\u0089\u0084\u0001\u0000\u0000\u0000\u0089\u0087\u0001"+
		"\u0000\u0000\u0000\u008a\u008b\u0001\u0000\u0000\u0000\u008b\u008c\u0005"+
		"\u0014\u0000\u0000\u008c\u008d\u0003\u0012\t\u0000\u008d\u0094\u0001\u0000"+
		"\u0000\u0000\u008e\u008f\u0007\u0007\u0000\u0000\u008f\u0090\u0003\u0012"+
		"\t\u0000\u0090\u0091\u0003\b\u0004\u0000\u0091\u0092\u0005\u0004\u0000"+
		"\u0000\u0092\u0094\u0001\u0000\u0000\u0000\u0093\u0083\u0001\u0000\u0000"+
		"\u0000\u0093\u008e\u0001\u0000\u0000\u0000\u0094\u0019\u0001\u0000\u0000"+
		"\u0000\u0095\u0099\u0003\u0010\b\u0000\u0096\u0099\u0003\u0016\u000b\u0000"+
		"\u0097\u0099\u0003\u0018\f\u0000\u0098\u0095\u0001\u0000\u0000\u0000\u0098"+
		"\u0096\u0001\u0000\u0000\u0000\u0098\u0097\u0001\u0000\u0000\u0000\u0099"+
		"\u009e\u0001\u0000\u0000\u0000\u009a\u009b\u0005\u001b\u0000\u0000\u009b"+
		"\u009d\u0003\u001a\r\u0000\u009c\u009a\u0001\u0000\u0000\u0000\u009d\u00a0"+
		"\u0001\u0000\u0000\u0000\u009e\u009c\u0001\u0000\u0000\u0000\u009e\u009f"+
		"\u0001\u0000\u0000\u0000\u009f\u00a1\u0001\u0000\u0000\u0000\u00a0\u009e"+
		"\u0001\u0000\u0000\u0000\u00a1\u00a2\u0005\u001c\u0000\u0000\u00a2\u00a5"+
		"\u0001\u0000\u0000\u0000\u00a3\u00a5\u0003\u000e\u0007\u0000\u00a4\u0098"+
		"\u0001\u0000\u0000\u0000\u00a4\u00a3\u0001\u0000\u0000\u0000\u00a5\u001b"+
		"\u0001\u0000\u0000\u0000\u00a6\u00a8\u0003\u001a\r\u0000\u00a7\u00a6\u0001"+
		"\u0000\u0000\u0000\u00a8\u00ab\u0001\u0000\u0000\u0000\u00a9\u00a7\u0001"+
		"\u0000\u0000\u0000\u00a9\u00aa\u0001\u0000\u0000\u0000\u00aa\u00ac\u0001"+
		"\u0000\u0000\u0000\u00ab\u00a9\u0001\u0000\u0000\u0000\u00ac\u00ad\u0005"+
		"\u0000\u0000\u0001\u00ad\u001d\u0001\u0000\u0000\u0000\u0013&13:@ET[b"+
		"irz\u0080\u0089\u0093\u0098\u009e\u00a4\u00a9";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}