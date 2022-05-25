package smthelusive.mova.gen;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

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
		DECREMENT=25, COMA=26, ALSO=27, DOT=28, MORETHAN=29, LESSTHAN=30, MOREOREQUAL=31, 
		LESSOREQUAL=32, NOTEQUAL=33, IDENTIFIER=34, INTEGER=35, DECIMAL=36, STRING=37, 
		COMMENT=38, SLAVAUKRAINI=39;
	public static final int
		RULE_value = 0, RULE_action = 1, RULE_expression = 2, RULE_assignment = 3, 
		RULE_output = 4, RULE_decrement = 5, RULE_increment = 6, RULE_slavaUkraini = 7, 
		RULE_command = 8, RULE_condition = 9, RULE_conditional = 10, RULE_loop = 11, 
		RULE_validStructure = 12, RULE_validProgram = 13;
	private static String[] makeRuleNames() {
		return new String[] {
			"value", "action", "expression", "assignment", "output", "decrement", 
			"increment", "slavaUkraini", "command", "condition", "conditional", "loop", 
			"validStructure", "validProgram"
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
			"DECREMENT", "COMA", "ALSO", "DOT", "MORETHAN", "LESSTHAN", "MOREOREQUAL", 
			"LESSOREQUAL", "NOTEQUAL", "IDENTIFIER", "INTEGER", "DECIMAL", "STRING", 
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).exitValue(this);
		}
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
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IDENTIFIER) | (1L << INTEGER) | (1L << DECIMAL) | (1L << STRING))) != 0)) ) {
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

	public static class ActionContext extends ParserRuleContext {
		public TerminalNode PLUS() { return getToken(MovaParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(MovaParser.MINUS, 0); }
		public TerminalNode MULTIPLY() { return getToken(MovaParser.MULTIPLY, 0); }
		public TerminalNode DIVIDE() { return getToken(MovaParser.DIVIDE, 0); }
		public TerminalNode PREFIX() { return getToken(MovaParser.PREFIX, 0); }
		public TerminalNode SUFFIX() { return getToken(MovaParser.SUFFIX, 0); }
		public TerminalNode WITH() { return getToken(MovaParser.WITH, 0); }
		public ActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_action; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).enterAction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).exitAction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MovaParserVisitor ) return ((MovaParserVisitor<? extends T>)visitor).visitAction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ActionContext action() throws RecognitionException {
		ActionContext _localctx = new ActionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_action);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PLUS) | (1L << MINUS) | (1L << MULTIPLY) | (1L << DIVIDE) | (1L << PREFIX) | (1L << SUFFIX) | (1L << WITH))) != 0)) ) {
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).exitExpression(this);
		}
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
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_expression, _p);
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
			case IDENTIFIER:
			case INTEGER:
			case DECIMAL:
			case STRING:
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

	public static class AssignmentContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(MovaParser.IDENTIFIER, 0); }
		public TerminalNode EQUALS() { return getToken(MovaParser.EQUALS, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MovaParserVisitor ) return ((MovaParserVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			match(IDENTIFIER);
			setState(55);
			match(EQUALS);
			setState(56);
			expression(0);
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
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public OutputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_output; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).enterOutput(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).exitOutput(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MovaParserVisitor ) return ((MovaParserVisitor<? extends T>)visitor).visitOutput(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OutputContext output() throws RecognitionException {
		OutputContext _localctx = new OutputContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_output);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			match(SHOW);
			setState(59);
			expression(0);
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

	public static class DecrementContext extends ParserRuleContext {
		public TerminalNode DECREMENT() { return getToken(MovaParser.DECREMENT, 0); }
		public TerminalNode IDENTIFIER() { return getToken(MovaParser.IDENTIFIER, 0); }
		public DecrementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decrement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).enterDecrement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).exitDecrement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MovaParserVisitor ) return ((MovaParserVisitor<? extends T>)visitor).visitDecrement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DecrementContext decrement() throws RecognitionException {
		DecrementContext _localctx = new DecrementContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_decrement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			match(DECREMENT);
			setState(62);
			match(IDENTIFIER);
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).enterIncrement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).exitIncrement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MovaParserVisitor ) return ((MovaParserVisitor<? extends T>)visitor).visitIncrement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IncrementContext increment() throws RecognitionException {
		IncrementContext _localctx = new IncrementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_increment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			match(INCREMENT);
			setState(65);
			match(IDENTIFIER);
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).enterSlavaUkraini(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).exitSlavaUkraini(this);
		}
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
			setState(67);
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).enterCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).exitCommand(this);
		}
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
			setState(73);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				{
				setState(69);
				assignment();
				}
				break;
			case DECREMENT:
				{
				setState(70);
				decrement();
				}
				break;
			case INCREMENT:
				{
				setState(71);
				increment();
				}
				break;
			case SHOW:
				{
				setState(72);
				output();
				}
				break;
			default:
				throw new NoViableAltException(this);
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
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).enterCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).exitCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MovaParserVisitor ) return ((MovaParserVisitor<? extends T>)visitor).visitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_condition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			expression(0);
			setState(79);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NOT) {
				{
				{
				setState(76);
				match(NOT);
				}
				}
				setState(81);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(86);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQUALS) | (1L << MORETHAN) | (1L << LESSTHAN) | (1L << MOREOREQUAL) | (1L << LESSOREQUAL) | (1L << NOTEQUAL))) != 0)) {
				{
				{
				setState(82);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQUALS) | (1L << MORETHAN) | (1L << LESSTHAN) | (1L << MOREOREQUAL) | (1L << LESSOREQUAL) | (1L << NOTEQUAL))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(83);
				expression(0);
				}
				}
				setState(88);
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
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
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
		public List<TerminalNode> ALSO() { return getTokens(MovaParser.ALSO); }
		public TerminalNode ALSO(int i) {
			return getToken(MovaParser.ALSO, i);
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).enterConditional(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).exitConditional(this);
		}
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
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(107); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(89);
				match(IF);
				setState(90);
				condition();
				setState(95);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==OR || _la==AND) {
					{
					{
					setState(91);
					_la = _input.LA(1);
					if ( !(_la==OR || _la==AND) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(92);
					condition();
					}
					}
					setState(97);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(98);
				_la = _input.LA(1);
				if ( !(_la==THEN || _la==COLON) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(99);
				command();
				setState(104);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(100);
						match(ALSO);
						setState(101);
						command();
						}
						} 
					}
					setState(106);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
				}
				}
				}
				setState(109); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==IF );
			setState(122);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OTHERWISE) {
				{
				{
				setState(111);
				match(OTHERWISE);
				setState(112);
				command();
				setState(117);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(113);
						match(ALSO);
						setState(114);
						command();
						}
						} 
					}
					setState(119);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
				}
				}
				}
				setState(124);
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
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode TIMES() { return getToken(MovaParser.TIMES, 0); }
		public TerminalNode DO() { return getToken(MovaParser.DO, 0); }
		public TerminalNode REPEAT() { return getToken(MovaParser.REPEAT, 0); }
		public List<TerminalNode> ALSO() { return getTokens(MovaParser.ALSO); }
		public TerminalNode ALSO(int i) {
			return getToken(MovaParser.ALSO, i);
		}
		public TerminalNode UNTIL() { return getToken(MovaParser.UNTIL, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public LoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).enterLoop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).exitLoop(this);
		}
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
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(146);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DO:
			case REPEAT:
				{
				{
				setState(125);
				_la = _input.LA(1);
				if ( !(_la==DO || _la==REPEAT) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(131);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case LPAREN:
				case IDENTIFIER:
				case INTEGER:
				case DECIMAL:
				case STRING:
					{
					{
					setState(126);
					expression(0);
					setState(127);
					match(TIMES);
					}
					}
					break;
				case UNTIL:
					{
					{
					setState(129);
					match(UNTIL);
					setState(130);
					condition();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(133);
				match(COLON);
				setState(134);
				command();
				setState(139);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(135);
						match(ALSO);
						setState(136);
						command();
						}
						} 
					}
					setState(141);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				}
				}
				}
				break;
			case SHOW:
			case INCREMENT:
			case DECREMENT:
			case IDENTIFIER:
				{
				{
				setState(142);
				command();
				setState(143);
				expression(0);
				setState(144);
				match(TIMES);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).enterValidStructure(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).exitValidStructure(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MovaParserVisitor ) return ((MovaParserVisitor<? extends T>)visitor).visitValidStructure(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValidStructureContext validStructure() throws RecognitionException {
		ValidStructureContext _localctx = new ValidStructureContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_validStructure);
		int _la;
		try {
			setState(163);
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
				setState(151);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
				case 1:
					{
					setState(148);
					command();
					}
					break;
				case 2:
					{
					setState(149);
					conditional();
					}
					break;
				case 3:
					{
					setState(150);
					loop();
					}
					break;
				}
				setState(157);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ALSO) {
					{
					{
					setState(153);
					match(ALSO);
					setState(154);
					validStructure();
					}
					}
					setState(159);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(160);
				match(DOT);
				}
				}
				break;
			case SLAVAUKRAINI:
				enterOuterAlt(_localctx, 2);
				{
				setState(162);
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
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).enterValidProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MovaParserListener ) ((MovaParserListener)listener).exitValidProgram(this);
		}
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
			setState(168);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DO) | (1L << REPEAT) | (1L << IF) | (1L << SHOW) | (1L << INCREMENT) | (1L << DECREMENT) | (1L << IDENTIFIER) | (1L << SLAVAUKRAINI))) != 0)) {
				{
				{
				setState(165);
				validStructure();
				}
				}
				setState(170);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(171);
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
		case 2:
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
		"\u0004\u0001\'\u00ae\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0003\u0002\'\b\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002"+
		"2\b\u0002\n\u0002\f\u00025\t\u0002\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007"+
		"\u0001\b\u0001\b\u0001\b\u0001\b\u0003\bJ\b\b\u0001\t\u0001\t\u0005\t"+
		"N\b\t\n\t\f\tQ\t\t\u0001\t\u0001\t\u0005\tU\b\t\n\t\f\tX\t\t\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0005\n^\b\n\n\n\f\na\t\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0005\ng\b\n\n\n\f\nj\t\n\u0004\nl\b\n\u000b\n\f\nm\u0001\n\u0001\n"+
		"\u0001\n\u0001\n\u0005\nt\b\n\n\n\f\nw\t\n\u0005\ny\b\n\n\n\f\n|\t\n\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003"+
		"\u000b\u0084\b\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0005"+
		"\u000b\u008a\b\u000b\n\u000b\f\u000b\u008d\t\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0003\u000b\u0093\b\u000b\u0001\f\u0001\f\u0001"+
		"\f\u0003\f\u0098\b\f\u0001\f\u0001\f\u0005\f\u009c\b\f\n\f\f\f\u009f\t"+
		"\f\u0001\f\u0001\f\u0001\f\u0003\f\u00a4\b\f\u0001\r\u0005\r\u00a7\b\r"+
		"\n\r\f\r\u00aa\t\r\u0001\r\u0001\r\u0001\r\u0000\u0001\u0004\u000e\u0000"+
		"\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u0000"+
		"\t\u0001\u0000\"%\u0001\u0000\t\u000f\u0001\u0000\u000b\f\u0001\u0000"+
		"\t\n\u0001\u0000\r\u000f\u0002\u0000\u0010\u0010\u001d!\u0001\u0000\u0015"+
		"\u0016\u0001\u0000\u0013\u0014\u0002\u0000\u0002\u0002\u0005\u0005\u00b5"+
		"\u0000\u001c\u0001\u0000\u0000\u0000\u0002\u001e\u0001\u0000\u0000\u0000"+
		"\u0004&\u0001\u0000\u0000\u0000\u00066\u0001\u0000\u0000\u0000\b:\u0001"+
		"\u0000\u0000\u0000\n=\u0001\u0000\u0000\u0000\f@\u0001\u0000\u0000\u0000"+
		"\u000eC\u0001\u0000\u0000\u0000\u0010I\u0001\u0000\u0000\u0000\u0012K"+
		"\u0001\u0000\u0000\u0000\u0014k\u0001\u0000\u0000\u0000\u0016\u0092\u0001"+
		"\u0000\u0000\u0000\u0018\u00a3\u0001\u0000\u0000\u0000\u001a\u00a8\u0001"+
		"\u0000\u0000\u0000\u001c\u001d\u0007\u0000\u0000\u0000\u001d\u0001\u0001"+
		"\u0000\u0000\u0000\u001e\u001f\u0007\u0001\u0000\u0000\u001f\u0003\u0001"+
		"\u0000\u0000\u0000 !\u0006\u0002\uffff\uffff\u0000!\"\u0005\u0011\u0000"+
		"\u0000\"#\u0003\u0004\u0002\u0000#$\u0005\u0012\u0000\u0000$\'\u0001\u0000"+
		"\u0000\u0000%\'\u0003\u0000\u0000\u0000& \u0001\u0000\u0000\u0000&%\u0001"+
		"\u0000\u0000\u0000\'3\u0001\u0000\u0000\u0000()\n\u0005\u0000\u0000)*"+
		"\u0007\u0002\u0000\u0000*2\u0003\u0004\u0002\u0006+,\n\u0004\u0000\u0000"+
		",-\u0007\u0003\u0000\u0000-2\u0003\u0004\u0002\u0005./\n\u0003\u0000\u0000"+
		"/0\u0007\u0004\u0000\u000002\u0003\u0004\u0002\u00041(\u0001\u0000\u0000"+
		"\u00001+\u0001\u0000\u0000\u00001.\u0001\u0000\u0000\u000025\u0001\u0000"+
		"\u0000\u000031\u0001\u0000\u0000\u000034\u0001\u0000\u0000\u00004\u0005"+
		"\u0001\u0000\u0000\u000053\u0001\u0000\u0000\u000067\u0005\"\u0000\u0000"+
		"78\u0005\u0010\u0000\u000089\u0003\u0004\u0002\u00009\u0007\u0001\u0000"+
		"\u0000\u0000:;\u0005\b\u0000\u0000;<\u0003\u0004\u0002\u0000<\t\u0001"+
		"\u0000\u0000\u0000=>\u0005\u0019\u0000\u0000>?\u0005\"\u0000\u0000?\u000b"+
		"\u0001\u0000\u0000\u0000@A\u0005\u0018\u0000\u0000AB\u0005\"\u0000\u0000"+
		"B\r\u0001\u0000\u0000\u0000CD\u0005\'\u0000\u0000D\u000f\u0001\u0000\u0000"+
		"\u0000EJ\u0003\u0006\u0003\u0000FJ\u0003\n\u0005\u0000GJ\u0003\f\u0006"+
		"\u0000HJ\u0003\b\u0004\u0000IE\u0001\u0000\u0000\u0000IF\u0001\u0000\u0000"+
		"\u0000IG\u0001\u0000\u0000\u0000IH\u0001\u0000\u0000\u0000J\u0011\u0001"+
		"\u0000\u0000\u0000KO\u0003\u0004\u0002\u0000LN\u0005\u0017\u0000\u0000"+
		"ML\u0001\u0000\u0000\u0000NQ\u0001\u0000\u0000\u0000OM\u0001\u0000\u0000"+
		"\u0000OP\u0001\u0000\u0000\u0000PV\u0001\u0000\u0000\u0000QO\u0001\u0000"+
		"\u0000\u0000RS\u0007\u0005\u0000\u0000SU\u0003\u0004\u0002\u0000TR\u0001"+
		"\u0000\u0000\u0000UX\u0001\u0000\u0000\u0000VT\u0001\u0000\u0000\u0000"+
		"VW\u0001\u0000\u0000\u0000W\u0013\u0001\u0000\u0000\u0000XV\u0001\u0000"+
		"\u0000\u0000YZ\u0005\u0006\u0000\u0000Z_\u0003\u0012\t\u0000[\\\u0007"+
		"\u0006\u0000\u0000\\^\u0003\u0012\t\u0000][\u0001\u0000\u0000\u0000^a"+
		"\u0001\u0000\u0000\u0000_]\u0001\u0000\u0000\u0000_`\u0001\u0000\u0000"+
		"\u0000`b\u0001\u0000\u0000\u0000a_\u0001\u0000\u0000\u0000bc\u0007\u0007"+
		"\u0000\u0000ch\u0003\u0010\b\u0000de\u0005\u001b\u0000\u0000eg\u0003\u0010"+
		"\b\u0000fd\u0001\u0000\u0000\u0000gj\u0001\u0000\u0000\u0000hf\u0001\u0000"+
		"\u0000\u0000hi\u0001\u0000\u0000\u0000il\u0001\u0000\u0000\u0000jh\u0001"+
		"\u0000\u0000\u0000kY\u0001\u0000\u0000\u0000lm\u0001\u0000\u0000\u0000"+
		"mk\u0001\u0000\u0000\u0000mn\u0001\u0000\u0000\u0000nz\u0001\u0000\u0000"+
		"\u0000op\u0005\u0007\u0000\u0000pu\u0003\u0010\b\u0000qr\u0005\u001b\u0000"+
		"\u0000rt\u0003\u0010\b\u0000sq\u0001\u0000\u0000\u0000tw\u0001\u0000\u0000"+
		"\u0000us\u0001\u0000\u0000\u0000uv\u0001\u0000\u0000\u0000vy\u0001\u0000"+
		"\u0000\u0000wu\u0001\u0000\u0000\u0000xo\u0001\u0000\u0000\u0000y|\u0001"+
		"\u0000\u0000\u0000zx\u0001\u0000\u0000\u0000z{\u0001\u0000\u0000\u0000"+
		"{\u0015\u0001\u0000\u0000\u0000|z\u0001\u0000\u0000\u0000}\u0083\u0007"+
		"\b\u0000\u0000~\u007f\u0003\u0004\u0002\u0000\u007f\u0080\u0005\u0004"+
		"\u0000\u0000\u0080\u0084\u0001\u0000\u0000\u0000\u0081\u0082\u0005\u0003"+
		"\u0000\u0000\u0082\u0084\u0003\u0012\t\u0000\u0083~\u0001\u0000\u0000"+
		"\u0000\u0083\u0081\u0001\u0000\u0000\u0000\u0084\u0085\u0001\u0000\u0000"+
		"\u0000\u0085\u0086\u0005\u0014\u0000\u0000\u0086\u008b\u0003\u0010\b\u0000"+
		"\u0087\u0088\u0005\u001b\u0000\u0000\u0088\u008a\u0003\u0010\b\u0000\u0089"+
		"\u0087\u0001\u0000\u0000\u0000\u008a\u008d\u0001\u0000\u0000\u0000\u008b"+
		"\u0089\u0001\u0000\u0000\u0000\u008b\u008c\u0001\u0000\u0000\u0000\u008c"+
		"\u0093\u0001\u0000\u0000\u0000\u008d\u008b\u0001\u0000\u0000\u0000\u008e"+
		"\u008f\u0003\u0010\b\u0000\u008f\u0090\u0003\u0004\u0002\u0000\u0090\u0091"+
		"\u0005\u0004\u0000\u0000\u0091\u0093\u0001\u0000\u0000\u0000\u0092}\u0001"+
		"\u0000\u0000\u0000\u0092\u008e\u0001\u0000\u0000\u0000\u0093\u0017\u0001"+
		"\u0000\u0000\u0000\u0094\u0098\u0003\u0010\b\u0000\u0095\u0098\u0003\u0014"+
		"\n\u0000\u0096\u0098\u0003\u0016\u000b\u0000\u0097\u0094\u0001\u0000\u0000"+
		"\u0000\u0097\u0095\u0001\u0000\u0000\u0000\u0097\u0096\u0001\u0000\u0000"+
		"\u0000\u0098\u009d\u0001\u0000\u0000\u0000\u0099\u009a\u0005\u001b\u0000"+
		"\u0000\u009a\u009c\u0003\u0018\f\u0000\u009b\u0099\u0001\u0000\u0000\u0000"+
		"\u009c\u009f\u0001\u0000\u0000\u0000\u009d\u009b\u0001\u0000\u0000\u0000"+
		"\u009d\u009e\u0001\u0000\u0000\u0000\u009e\u00a0\u0001\u0000\u0000\u0000"+
		"\u009f\u009d\u0001\u0000\u0000\u0000\u00a0\u00a1\u0005\u001c\u0000\u0000"+
		"\u00a1\u00a4\u0001\u0000\u0000\u0000\u00a2\u00a4\u0003\u000e\u0007\u0000"+
		"\u00a3\u0097\u0001\u0000\u0000\u0000\u00a3\u00a2\u0001\u0000\u0000\u0000"+
		"\u00a4\u0019\u0001\u0000\u0000\u0000\u00a5\u00a7\u0003\u0018\f\u0000\u00a6"+
		"\u00a5\u0001\u0000\u0000\u0000\u00a7\u00aa\u0001\u0000\u0000\u0000\u00a8"+
		"\u00a6\u0001\u0000\u0000\u0000\u00a8\u00a9\u0001\u0000\u0000\u0000\u00a9"+
		"\u00ab\u0001\u0000\u0000\u0000\u00aa\u00a8\u0001\u0000\u0000\u0000\u00ab"+
		"\u00ac\u0005\u0000\u0000\u0001\u00ac\u001b\u0001\u0000\u0000\u0000\u0012"+
		"&13IOV_hmuz\u0083\u008b\u0092\u0097\u009d\u00a3\u00a8";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}