// Generated from /Users/nbziubenko/Documents/work/pd_repos/mova/src/main/antlr4/MovaParser.g4 by ANTLR 4.10.1
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
		DECREMENT=25, COMMA=26, ALSO=27, DOT=28, GREATERTHAN=29, LESSTHAN=30, 
		GREATEROREQUAL=31, LESSOREQUAL=32, NOTEQUAL=33, INTEGER=34, DECIMAL=35, 
		STRING=36, IDENTIFIER=37, COMMENT=38, SLAVAUKRAINI=39;
	public static final int
		RULE_value = 0, RULE_expression = 1, RULE_decrement = 2, RULE_increment = 3, 
		RULE_allKindsExpression = 4, RULE_assignment = 5, RULE_output = 6, RULE_slavaUkraini = 7, 
		RULE_command = 8, RULE_condition = 9, RULE_conditional = 10, RULE_loop = 11, 
		RULE_validStructure = 12, RULE_validProgram = 13;
	private static String[] makeRuleNames() {
		return new String[] {
			"value", "expression", "decrement", "increment", "allKindsExpression", 
			"assignment", "output", "slavaUkraini", "command", "condition", "conditional", 
			"loop", "validStructure", "validProgram"
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
			"DECREMENT", "COMMA", "ALSO", "DOT", "GREATERTHAN", "LESSTHAN", "GREATEROREQUAL", 
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
			setState(28);
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
			setState(36);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				{
				setState(31);
				match(LPAREN);
				setState(32);
				expression(0);
				setState(33);
				match(RPAREN);
				}
				break;
			case INTEGER:
			case DECIMAL:
			case STRING:
			case IDENTIFIER:
				{
				setState(35);
				value();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(49);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(47);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(38);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(39);
						_la = _input.LA(1);
						if ( !(_la==MULTIPLY || _la==DIVIDE) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(40);
						expression(6);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(41);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(42);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(43);
						expression(5);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(44);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(45);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PREFIX) | (1L << SUFFIX) | (1L << WITH))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(46);
						expression(4);
						}
						break;
					}
					} 
				}
				setState(51);
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
			setState(56);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DECREMENT:
				enterOuterAlt(_localctx, 1);
				{
				setState(52);
				match(DECREMENT);
				setState(53);
				match(IDENTIFIER);
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(54);
				match(IDENTIFIER);
				setState(55);
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
			setState(62);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INCREMENT:
				enterOuterAlt(_localctx, 1);
				{
				setState(58);
				match(INCREMENT);
				setState(59);
				match(IDENTIFIER);
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(60);
				match(IDENTIFIER);
				setState(61);
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
			setState(67);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				setState(64);
				expression(0);
				}
				break;
			case 2:
				{
				setState(65);
				increment();
				}
				break;
			case 3:
				{
				setState(66);
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
			setState(69);
			match(IDENTIFIER);
			setState(70);
			match(EQUALS);
			setState(71);
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
			setState(73);
			match(SHOW);
			setState(74);
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
			setState(76);
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
			setState(82);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				{
				setState(78);
				assignment();
				}
				break;
			case 2:
				{
				setState(79);
				decrement();
				}
				break;
			case 3:
				{
				setState(80);
				increment();
				}
				break;
			case 4:
				{
				setState(81);
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

	public static class ConditionContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(MovaParser.LPAREN, 0); }
		public List<ConditionContext> condition() {
			return getRuleContexts(ConditionContext.class);
		}
		public ConditionContext condition(int i) {
			return getRuleContext(ConditionContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(MovaParser.RPAREN, 0); }
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
		public List<TerminalNode> GREATERTHAN() { return getTokens(MovaParser.GREATERTHAN); }
		public TerminalNode GREATERTHAN(int i) {
			return getToken(MovaParser.GREATERTHAN, i);
		}
		public List<TerminalNode> LESSTHAN() { return getTokens(MovaParser.LESSTHAN); }
		public TerminalNode LESSTHAN(int i) {
			return getToken(MovaParser.LESSTHAN, i);
		}
		public List<TerminalNode> GREATEROREQUAL() { return getTokens(MovaParser.GREATEROREQUAL); }
		public TerminalNode GREATEROREQUAL(int i) {
			return getToken(MovaParser.GREATEROREQUAL, i);
		}
		public List<TerminalNode> LESSOREQUAL() { return getTokens(MovaParser.LESSOREQUAL); }
		public TerminalNode LESSOREQUAL(int i) {
			return getToken(MovaParser.LESSOREQUAL, i);
		}
		public List<TerminalNode> NOTEQUAL() { return getTokens(MovaParser.NOTEQUAL); }
		public TerminalNode NOTEQUAL(int i) {
			return getToken(MovaParser.NOTEQUAL, i);
		}
		public TerminalNode AND() { return getToken(MovaParser.AND, 0); }
		public TerminalNode OR() { return getToken(MovaParser.OR, 0); }
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
		return condition(0);
	}

	private ConditionContext condition(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ConditionContext _localctx = new ConditionContext(_ctx, _parentState);
		ConditionContext _prevctx = _localctx;
		int _startState = 18;
		enterRecursionRule(_localctx, 18, RULE_condition, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				{
				setState(85);
				match(LPAREN);
				setState(86);
				condition(0);
				setState(87);
				match(RPAREN);
				}
				break;
			case 2:
				{
				setState(90);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(89);
					match(NOT);
					}
				}

				setState(92);
				allKindsExpression();
				setState(100);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(94);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==NOT) {
							{
							setState(93);
							match(NOT);
							}
						}

						setState(96);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQUALS) | (1L << GREATERTHAN) | (1L << LESSTHAN) | (1L << GREATEROREQUAL) | (1L << LESSOREQUAL) | (1L << NOTEQUAL))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(97);
						allKindsExpression();
						}
						} 
					}
					setState(102);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
				}
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(110);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ConditionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_condition);
					setState(105);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(106);
					_la = _input.LA(1);
					if ( !(_la==OR || _la==AND) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(107);
					condition(4);
					}
					} 
				}
				setState(112);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
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

	public static class ConditionalContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(MovaParser.IF, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public List<ValidStructureContext> validStructure() {
			return getRuleContexts(ValidStructureContext.class);
		}
		public ValidStructureContext validStructure(int i) {
			return getRuleContext(ValidStructureContext.class,i);
		}
		public TerminalNode OTHERWISE() { return getToken(MovaParser.OTHERWISE, 0); }
		public TerminalNode THEN() { return getToken(MovaParser.THEN, 0); }
		public TerminalNode COLON() { return getToken(MovaParser.COLON, 0); }
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
		enterRule(_localctx, 20, RULE_conditional);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			match(IF);
			setState(114);
			condition(0);
			setState(116);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==THEN || _la==COLON) {
				{
				setState(115);
				_la = _input.LA(1);
				if ( !(_la==THEN || _la==COLON) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(118);
			validStructure();
			setState(121);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(119);
				match(OTHERWISE);
				setState(120);
				validStructure();
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

	public static class LoopContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(MovaParser.COLON, 0); }
		public ValidStructureContext validStructure() {
			return getRuleContext(ValidStructureContext.class,0);
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
		enterRule(_localctx, 22, RULE_loop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(139);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				{
				{
				setState(123);
				_la = _input.LA(1);
				if ( !(_la==DO || _la==REPEAT) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(129);
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
					setState(124);
					allKindsExpression();
					setState(125);
					match(TIMES);
					}
					}
					break;
				case UNTIL:
					{
					{
					setState(127);
					match(UNTIL);
					setState(128);
					condition(0);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(131);
				match(COLON);
				setState(132);
				validStructure();
				}
				}
				break;
			case 2:
				{
				{
				setState(134);
				_la = _input.LA(1);
				if ( !(_la==DO || _la==REPEAT) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(135);
				validStructure();
				setState(136);
				allKindsExpression();
				setState(137);
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
		public CommandContext command() {
			return getRuleContext(CommandContext.class,0);
		}
		public ConditionalContext conditional() {
			return getRuleContext(ConditionalContext.class,0);
		}
		public LoopContext loop() {
			return getRuleContext(LoopContext.class,0);
		}
		public SlavaUkrainiContext slavaUkraini() {
			return getRuleContext(SlavaUkrainiContext.class,0);
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
		enterRule(_localctx, 24, RULE_validStructure);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(145);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SHOW:
			case INCREMENT:
			case DECREMENT:
			case IDENTIFIER:
				{
				setState(141);
				command();
				}
				break;
			case IF:
				{
				setState(142);
				conditional();
				}
				break;
			case DO:
			case REPEAT:
				{
				setState(143);
				loop();
				}
				break;
			case SLAVAUKRAINI:
				{
				setState(144);
				slavaUkraini();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(151);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(147);
					match(ALSO);
					setState(148);
					validStructure();
					}
					} 
				}
				setState(153);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
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

	public static class ValidProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(MovaParser.EOF, 0); }
		public List<ValidStructureContext> validStructure() {
			return getRuleContexts(ValidStructureContext.class);
		}
		public ValidStructureContext validStructure(int i) {
			return getRuleContext(ValidStructureContext.class,i);
		}
		public List<TerminalNode> DOT() { return getTokens(MovaParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(MovaParser.DOT, i);
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
		enterRule(_localctx, 26, RULE_validProgram);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(159);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DO) | (1L << REPEAT) | (1L << IF) | (1L << SHOW) | (1L << INCREMENT) | (1L << DECREMENT) | (1L << IDENTIFIER) | (1L << SLAVAUKRAINI))) != 0)) {
				{
				{
				setState(154);
				validStructure();
				setState(155);
				match(DOT);
				}
				}
				setState(161);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(162);
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
		case 9:
			return condition_sempred((ConditionContext)_localctx, predIndex);
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
	private boolean condition_sempred(ConditionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return precpred(_ctx, 3);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\'\u00a5\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001%\b\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u00010\b\u0001\n\u0001\f\u0001"+
		"3\t\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002"+
		"9\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003"+
		"?\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004D\b\u0004\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0003\b"+
		"S\b\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003\t[\b\t\u0001"+
		"\t\u0001\t\u0003\t_\b\t\u0001\t\u0001\t\u0005\tc\b\t\n\t\f\tf\t\t\u0003"+
		"\th\b\t\u0001\t\u0001\t\u0001\t\u0005\tm\b\t\n\t\f\tp\t\t\u0001\n\u0001"+
		"\n\u0001\n\u0003\nu\b\n\u0001\n\u0001\n\u0001\n\u0003\nz\b\n\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b"+
		"\u0082\b\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u008c\b\u000b\u0001\f"+
		"\u0001\f\u0001\f\u0001\f\u0003\f\u0092\b\f\u0001\f\u0001\f\u0005\f\u0096"+
		"\b\f\n\f\f\f\u0099\t\f\u0001\r\u0001\r\u0001\r\u0005\r\u009e\b\r\n\r\f"+
		"\r\u00a1\t\r\u0001\r\u0001\r\u0001\r\u0000\u0002\u0002\u0012\u000e\u0000"+
		"\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u0000"+
		"\b\u0001\u0000\"%\u0001\u0000\u000b\f\u0001\u0000\t\n\u0001\u0000\r\u000f"+
		"\u0002\u0000\u0010\u0010\u001d!\u0001\u0000\u0015\u0016\u0001\u0000\u0013"+
		"\u0014\u0002\u0000\u0002\u0002\u0005\u0005\u00af\u0000\u001c\u0001\u0000"+
		"\u0000\u0000\u0002$\u0001\u0000\u0000\u0000\u00048\u0001\u0000\u0000\u0000"+
		"\u0006>\u0001\u0000\u0000\u0000\bC\u0001\u0000\u0000\u0000\nE\u0001\u0000"+
		"\u0000\u0000\fI\u0001\u0000\u0000\u0000\u000eL\u0001\u0000\u0000\u0000"+
		"\u0010R\u0001\u0000\u0000\u0000\u0012g\u0001\u0000\u0000\u0000\u0014q"+
		"\u0001\u0000\u0000\u0000\u0016\u008b\u0001\u0000\u0000\u0000\u0018\u0091"+
		"\u0001\u0000\u0000\u0000\u001a\u009f\u0001\u0000\u0000\u0000\u001c\u001d"+
		"\u0007\u0000\u0000\u0000\u001d\u0001\u0001\u0000\u0000\u0000\u001e\u001f"+
		"\u0006\u0001\uffff\uffff\u0000\u001f \u0005\u0011\u0000\u0000 !\u0003"+
		"\u0002\u0001\u0000!\"\u0005\u0012\u0000\u0000\"%\u0001\u0000\u0000\u0000"+
		"#%\u0003\u0000\u0000\u0000$\u001e\u0001\u0000\u0000\u0000$#\u0001\u0000"+
		"\u0000\u0000%1\u0001\u0000\u0000\u0000&\'\n\u0005\u0000\u0000\'(\u0007"+
		"\u0001\u0000\u0000(0\u0003\u0002\u0001\u0006)*\n\u0004\u0000\u0000*+\u0007"+
		"\u0002\u0000\u0000+0\u0003\u0002\u0001\u0005,-\n\u0003\u0000\u0000-.\u0007"+
		"\u0003\u0000\u0000.0\u0003\u0002\u0001\u0004/&\u0001\u0000\u0000\u0000"+
		"/)\u0001\u0000\u0000\u0000/,\u0001\u0000\u0000\u000003\u0001\u0000\u0000"+
		"\u00001/\u0001\u0000\u0000\u000012\u0001\u0000\u0000\u00002\u0003\u0001"+
		"\u0000\u0000\u000031\u0001\u0000\u0000\u000045\u0005\u0019\u0000\u0000"+
		"59\u0005%\u0000\u000067\u0005%\u0000\u000079\u0005\u0019\u0000\u00008"+
		"4\u0001\u0000\u0000\u000086\u0001\u0000\u0000\u00009\u0005\u0001\u0000"+
		"\u0000\u0000:;\u0005\u0018\u0000\u0000;?\u0005%\u0000\u0000<=\u0005%\u0000"+
		"\u0000=?\u0005\u0018\u0000\u0000>:\u0001\u0000\u0000\u0000><\u0001\u0000"+
		"\u0000\u0000?\u0007\u0001\u0000\u0000\u0000@D\u0003\u0002\u0001\u0000"+
		"AD\u0003\u0006\u0003\u0000BD\u0003\u0004\u0002\u0000C@\u0001\u0000\u0000"+
		"\u0000CA\u0001\u0000\u0000\u0000CB\u0001\u0000\u0000\u0000D\t\u0001\u0000"+
		"\u0000\u0000EF\u0005%\u0000\u0000FG\u0005\u0010\u0000\u0000GH\u0003\b"+
		"\u0004\u0000H\u000b\u0001\u0000\u0000\u0000IJ\u0005\b\u0000\u0000JK\u0003"+
		"\b\u0004\u0000K\r\u0001\u0000\u0000\u0000LM\u0005\'\u0000\u0000M\u000f"+
		"\u0001\u0000\u0000\u0000NS\u0003\n\u0005\u0000OS\u0003\u0004\u0002\u0000"+
		"PS\u0003\u0006\u0003\u0000QS\u0003\f\u0006\u0000RN\u0001\u0000\u0000\u0000"+
		"RO\u0001\u0000\u0000\u0000RP\u0001\u0000\u0000\u0000RQ\u0001\u0000\u0000"+
		"\u0000S\u0011\u0001\u0000\u0000\u0000TU\u0006\t\uffff\uffff\u0000UV\u0005"+
		"\u0011\u0000\u0000VW\u0003\u0012\t\u0000WX\u0005\u0012\u0000\u0000Xh\u0001"+
		"\u0000\u0000\u0000Y[\u0005\u0017\u0000\u0000ZY\u0001\u0000\u0000\u0000"+
		"Z[\u0001\u0000\u0000\u0000[\\\u0001\u0000\u0000\u0000\\d\u0003\b\u0004"+
		"\u0000]_\u0005\u0017\u0000\u0000^]\u0001\u0000\u0000\u0000^_\u0001\u0000"+
		"\u0000\u0000_`\u0001\u0000\u0000\u0000`a\u0007\u0004\u0000\u0000ac\u0003"+
		"\b\u0004\u0000b^\u0001\u0000\u0000\u0000cf\u0001\u0000\u0000\u0000db\u0001"+
		"\u0000\u0000\u0000de\u0001\u0000\u0000\u0000eh\u0001\u0000\u0000\u0000"+
		"fd\u0001\u0000\u0000\u0000gT\u0001\u0000\u0000\u0000gZ\u0001\u0000\u0000"+
		"\u0000hn\u0001\u0000\u0000\u0000ij\n\u0003\u0000\u0000jk\u0007\u0005\u0000"+
		"\u0000km\u0003\u0012\t\u0004li\u0001\u0000\u0000\u0000mp\u0001\u0000\u0000"+
		"\u0000nl\u0001\u0000\u0000\u0000no\u0001\u0000\u0000\u0000o\u0013\u0001"+
		"\u0000\u0000\u0000pn\u0001\u0000\u0000\u0000qr\u0005\u0006\u0000\u0000"+
		"rt\u0003\u0012\t\u0000su\u0007\u0006\u0000\u0000ts\u0001\u0000\u0000\u0000"+
		"tu\u0001\u0000\u0000\u0000uv\u0001\u0000\u0000\u0000vy\u0003\u0018\f\u0000"+
		"wx\u0005\u0007\u0000\u0000xz\u0003\u0018\f\u0000yw\u0001\u0000\u0000\u0000"+
		"yz\u0001\u0000\u0000\u0000z\u0015\u0001\u0000\u0000\u0000{\u0081\u0007"+
		"\u0007\u0000\u0000|}\u0003\b\u0004\u0000}~\u0005\u0004\u0000\u0000~\u0082"+
		"\u0001\u0000\u0000\u0000\u007f\u0080\u0005\u0003\u0000\u0000\u0080\u0082"+
		"\u0003\u0012\t\u0000\u0081|\u0001\u0000\u0000\u0000\u0081\u007f\u0001"+
		"\u0000\u0000\u0000\u0082\u0083\u0001\u0000\u0000\u0000\u0083\u0084\u0005"+
		"\u0014\u0000\u0000\u0084\u0085\u0003\u0018\f\u0000\u0085\u008c\u0001\u0000"+
		"\u0000\u0000\u0086\u0087\u0007\u0007\u0000\u0000\u0087\u0088\u0003\u0018"+
		"\f\u0000\u0088\u0089\u0003\b\u0004\u0000\u0089\u008a\u0005\u0004\u0000"+
		"\u0000\u008a\u008c\u0001\u0000\u0000\u0000\u008b{\u0001\u0000\u0000\u0000"+
		"\u008b\u0086\u0001\u0000\u0000\u0000\u008c\u0017\u0001\u0000\u0000\u0000"+
		"\u008d\u0092\u0003\u0010\b\u0000\u008e\u0092\u0003\u0014\n\u0000\u008f"+
		"\u0092\u0003\u0016\u000b\u0000\u0090\u0092\u0003\u000e\u0007\u0000\u0091"+
		"\u008d\u0001\u0000\u0000\u0000\u0091\u008e\u0001\u0000\u0000\u0000\u0091"+
		"\u008f\u0001\u0000\u0000\u0000\u0091\u0090\u0001\u0000\u0000\u0000\u0092"+
		"\u0097\u0001\u0000\u0000\u0000\u0093\u0094\u0005\u001b\u0000\u0000\u0094"+
		"\u0096\u0003\u0018\f\u0000\u0095\u0093\u0001\u0000\u0000\u0000\u0096\u0099"+
		"\u0001\u0000\u0000\u0000\u0097\u0095\u0001\u0000\u0000\u0000\u0097\u0098"+
		"\u0001\u0000\u0000\u0000\u0098\u0019\u0001\u0000\u0000\u0000\u0099\u0097"+
		"\u0001\u0000\u0000\u0000\u009a\u009b\u0003\u0018\f\u0000\u009b\u009c\u0005"+
		"\u001c\u0000\u0000\u009c\u009e\u0001\u0000\u0000\u0000\u009d\u009a\u0001"+
		"\u0000\u0000\u0000\u009e\u00a1\u0001\u0000\u0000\u0000\u009f\u009d\u0001"+
		"\u0000\u0000\u0000\u009f\u00a0\u0001\u0000\u0000\u0000\u00a0\u00a2\u0001"+
		"\u0000\u0000\u0000\u00a1\u009f\u0001\u0000\u0000\u0000\u00a2\u00a3\u0005"+
		"\u0000\u0000\u0001\u00a3\u001b\u0001\u0000\u0000\u0000\u0013$/18>CRZ^"+
		"dgnty\u0081\u008b\u0091\u0097\u009f";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}