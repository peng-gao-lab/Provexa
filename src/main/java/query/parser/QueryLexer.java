// Generated from QueryLexer.g4 by ANTLR 4.9.2

	package query.parser;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class QueryLexer extends Lexer {
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"SEMICOLON", "COMMA", "ASTERISK", "LEFTBRACKET", "RIGHTBRACKET", "LEFTBRACE", 
			"RIGHTBRACE", "LEFTSQUARE", "RIGHTSQUARE", "AND", "OR", "NOT", "IN", 
			"FROM", "WITH", "BACK", "FORWARD", "TRACK", "SEARCH", "AS", "DISPLAY", 
			"EXPORT", "RETURN", "SELECT", "CONNECTDB", "DB", "WHERE", "INCLUDE", 
			"EXCLUDE", "NODES", "EDGES", "LIMIT", "TIME", "STEP", "MS", "SECOND", 
			"MINUTE", "TYPE", "NAME", "PATH", "DSTIP", "DSTPORT", "SRCIP", "SRCPORT", 
			"PID", "EXENAME", "EXEPATH", "CMDLINE", "OPTYPE", "IDSTR", "SRCID", "DSTID", 
			"STARTTIME", "ENDTIME", "AMOUNT", "PROCESS", "FILE", "NETWORK", "READ", 
			"WRITE", "EXECVE", "NULL", "ARROW", "EQ", "NEQ", "GT", "GEQ", "LT", "LEQ", 
			"LIKE", "UNION", "INTERSECTION", "DIFFERENCE", "LOGAND", "LOGOR", "INT", 
			"POSINT", "NEGINT", "STRING", "DoubleStringCharacter", "SingleStringCharacter", 
			"DIGIT", "ID", "ID_LETTER", "NEWLINE", "WS", "COMMENT", "LINE_COMMENT"
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


	public QueryLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "QueryLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2T\u027f\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\4U\tU\4V\tV\4W\tW\4X\tX\4Y\tY\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6"+
		"\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3"+
		"\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20"+
		"\3\20\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25"+
		"\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31"+
		"\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\33"+
		"\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\35"+
		"\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3\37"+
		"\3\37\3\37\3 \3 \3 \3 \3 \3 \3!\3!\3!\3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3#"+
		"\3#\3#\3#\3#\3$\3$\3$\3%\3%\3&\3&\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3(\3"+
		")\3)\3)\3)\3)\3*\3*\3*\3*\3*\3*\3+\3+\3+\3+\3+\3+\3+\3+\3,\3,\3,\3,\3"+
		",\3,\3-\3-\3-\3-\3-\3-\3-\3-\3.\3.\3.\3.\3/\3/\3/\3/\3/\3/\3/\3/\3\60"+
		"\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\61\3\61\3\61\3\61\3\61\3\61\3\61"+
		"\3\61\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\63\3\63\3\63\3\64\3\64\3\64"+
		"\3\64\3\64\3\64\3\65\3\65\3\65\3\65\3\65\3\65\3\66\3\66\3\66\3\66\3\66"+
		"\3\66\3\66\3\66\3\66\3\66\3\67\3\67\3\67\3\67\3\67\3\67\3\67\3\67\38\3"+
		"8\38\38\38\38\38\39\39\39\39\39\39\39\39\3:\3:\3:\3:\3:\3;\3;\3;\3;\3"+
		";\3;\3;\3;\3<\3<\3<\3<\3<\3=\3=\3=\3=\3=\3=\3>\3>\3>\3>\3>\3>\3>\3?\3"+
		"?\3?\3?\3?\3@\3@\3@\3A\3A\3B\3B\3B\3C\3C\3D\3D\3D\3E\3E\3F\3F\3F\3G\3"+
		"G\3G\3G\3G\3H\3H\3I\3I\3J\3J\3K\3K\3K\3L\3L\3L\3M\3M\5M\u0225\nM\3N\6"+
		"N\u0228\nN\rN\16N\u0229\3O\3O\6O\u022e\nO\rO\16O\u022f\3P\3P\7P\u0234"+
		"\nP\fP\16P\u0237\13P\3P\3P\3P\7P\u023c\nP\fP\16P\u023f\13P\3P\5P\u0242"+
		"\nP\3Q\3Q\3R\3R\3S\3S\3T\3T\3T\7T\u024d\nT\fT\16T\u0250\13T\3U\3U\3V\6"+
		"V\u0255\nV\rV\16V\u0256\3V\3V\3W\6W\u025c\nW\rW\16W\u025d\3W\3W\3X\3X"+
		"\3X\3X\7X\u0266\nX\fX\16X\u0269\13X\3X\3X\3X\3X\3X\3Y\3Y\3Y\3Y\7Y\u0274"+
		"\nY\fY\16Y\u0277\13Y\3Y\5Y\u027a\nY\3Y\3Y\3Y\3Y\4\u0267\u0275\2Z\3\3\5"+
		"\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21"+
		"!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!"+
		"A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\65i\66k\67m8o9q:s"+
		";u<w=y>{?}@\177A\u0081B\u0083C\u0085D\u0087E\u0089F\u008bG\u008dH\u008f"+
		"I\u0091J\u0093K\u0095L\u0097M\u0099N\u009b\2\u009d\2\u009fO\u00a1\2\u00a3"+
		"\2\u00a5\2\u00a7P\u00a9\2\u00abQ\u00adR\u00afS\u00b1T\3\2\b\5\2\f\f\17"+
		"\17$$\5\2\f\f\17\17))\3\2\62;\5\2C\\aac|\4\2\f\f\17\17\5\2\13\f\16\17"+
		"\"\"\2\u0285\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2"+
		"\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2"+
		"\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2"+
		"\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2"+
		"\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3"+
		"\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2"+
		"\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2"+
		"S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3"+
		"\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2"+
		"\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2"+
		"y\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081\3\2\2\2\2\u0083"+
		"\3\2\2\2\2\u0085\3\2\2\2\2\u0087\3\2\2\2\2\u0089\3\2\2\2\2\u008b\3\2\2"+
		"\2\2\u008d\3\2\2\2\2\u008f\3\2\2\2\2\u0091\3\2\2\2\2\u0093\3\2\2\2\2\u0095"+
		"\3\2\2\2\2\u0097\3\2\2\2\2\u0099\3\2\2\2\2\u009f\3\2\2\2\2\u00a7\3\2\2"+
		"\2\2\u00ab\3\2\2\2\2\u00ad\3\2\2\2\2\u00af\3\2\2\2\2\u00b1\3\2\2\2\3\u00b3"+
		"\3\2\2\2\5\u00b5\3\2\2\2\7\u00b7\3\2\2\2\t\u00b9\3\2\2\2\13\u00bb\3\2"+
		"\2\2\r\u00bd\3\2\2\2\17\u00bf\3\2\2\2\21\u00c1\3\2\2\2\23\u00c3\3\2\2"+
		"\2\25\u00c5\3\2\2\2\27\u00c9\3\2\2\2\31\u00cc\3\2\2\2\33\u00d0\3\2\2\2"+
		"\35\u00d3\3\2\2\2\37\u00d8\3\2\2\2!\u00dd\3\2\2\2#\u00e2\3\2\2\2%\u00ea"+
		"\3\2\2\2\'\u00f0\3\2\2\2)\u00f7\3\2\2\2+\u00fa\3\2\2\2-\u0102\3\2\2\2"+
		"/\u0109\3\2\2\2\61\u0110\3\2\2\2\63\u0117\3\2\2\2\65\u0121\3\2\2\2\67"+
		"\u0124\3\2\2\29\u012a\3\2\2\2;\u0132\3\2\2\2=\u013a\3\2\2\2?\u0140\3\2"+
		"\2\2A\u0146\3\2\2\2C\u014c\3\2\2\2E\u0151\3\2\2\2G\u0156\3\2\2\2I\u0159"+
		"\3\2\2\2K\u015b\3\2\2\2M\u015d\3\2\2\2O\u0162\3\2\2\2Q\u0167\3\2\2\2S"+
		"\u016c\3\2\2\2U\u0172\3\2\2\2W\u017a\3\2\2\2Y\u0180\3\2\2\2[\u0188\3\2"+
		"\2\2]\u018c\3\2\2\2_\u0194\3\2\2\2a\u019c\3\2\2\2c\u01a4\3\2\2\2e\u01ab"+
		"\3\2\2\2g\u01ae\3\2\2\2i\u01b4\3\2\2\2k\u01ba\3\2\2\2m\u01c4\3\2\2\2o"+
		"\u01cc\3\2\2\2q\u01d3\3\2\2\2s\u01db\3\2\2\2u\u01e0\3\2\2\2w\u01e8\3\2"+
		"\2\2y\u01ed\3\2\2\2{\u01f3\3\2\2\2}\u01fa\3\2\2\2\177\u01ff\3\2\2\2\u0081"+
		"\u0202\3\2\2\2\u0083\u0204\3\2\2\2\u0085\u0207\3\2\2\2\u0087\u0209\3\2"+
		"\2\2\u0089\u020c\3\2\2\2\u008b\u020e\3\2\2\2\u008d\u0211\3\2\2\2\u008f"+
		"\u0216\3\2\2\2\u0091\u0218\3\2\2\2\u0093\u021a\3\2\2\2\u0095\u021c\3\2"+
		"\2\2\u0097\u021f\3\2\2\2\u0099\u0224\3\2\2\2\u009b\u0227\3\2\2\2\u009d"+
		"\u022b\3\2\2\2\u009f\u0241\3\2\2\2\u00a1\u0243\3\2\2\2\u00a3\u0245\3\2"+
		"\2\2\u00a5\u0247\3\2\2\2\u00a7\u0249\3\2\2\2\u00a9\u0251\3\2\2\2\u00ab"+
		"\u0254\3\2\2\2\u00ad\u025b\3\2\2\2\u00af\u0261\3\2\2\2\u00b1\u026f\3\2"+
		"\2\2\u00b3\u00b4\7=\2\2\u00b4\4\3\2\2\2\u00b5\u00b6\7.\2\2\u00b6\6\3\2"+
		"\2\2\u00b7\u00b8\7,\2\2\u00b8\b\3\2\2\2\u00b9\u00ba\7*\2\2\u00ba\n\3\2"+
		"\2\2\u00bb\u00bc\7+\2\2\u00bc\f\3\2\2\2\u00bd\u00be\7}\2\2\u00be\16\3"+
		"\2\2\2\u00bf\u00c0\7\177\2\2\u00c0\20\3\2\2\2\u00c1\u00c2\7]\2\2\u00c2"+
		"\22\3\2\2\2\u00c3\u00c4\7_\2\2\u00c4\24\3\2\2\2\u00c5\u00c6\7c\2\2\u00c6"+
		"\u00c7\7p\2\2\u00c7\u00c8\7f\2\2\u00c8\26\3\2\2\2\u00c9\u00ca\7q\2\2\u00ca"+
		"\u00cb\7t\2\2\u00cb\30\3\2\2\2\u00cc\u00cd\7p\2\2\u00cd\u00ce\7q\2\2\u00ce"+
		"\u00cf\7v\2\2\u00cf\32\3\2\2\2\u00d0\u00d1\7k\2\2\u00d1\u00d2\7p\2\2\u00d2"+
		"\34\3\2\2\2\u00d3\u00d4\7h\2\2\u00d4\u00d5\7t\2\2\u00d5\u00d6\7q\2\2\u00d6"+
		"\u00d7\7o\2\2\u00d7\36\3\2\2\2\u00d8\u00d9\7y\2\2\u00d9\u00da\7k\2\2\u00da"+
		"\u00db\7v\2\2\u00db\u00dc\7j\2\2\u00dc \3\2\2\2\u00dd\u00de\7d\2\2\u00de"+
		"\u00df\7c\2\2\u00df\u00e0\7e\2\2\u00e0\u00e1\7m\2\2\u00e1\"\3\2\2\2\u00e2"+
		"\u00e3\7h\2\2\u00e3\u00e4\7q\2\2\u00e4\u00e5\7t\2\2\u00e5\u00e6\7y\2\2"+
		"\u00e6\u00e7\7c\2\2\u00e7\u00e8\7t\2\2\u00e8\u00e9\7f\2\2\u00e9$\3\2\2"+
		"\2\u00ea\u00eb\7v\2\2\u00eb\u00ec\7t\2\2\u00ec\u00ed\7c\2\2\u00ed\u00ee"+
		"\7e\2\2\u00ee\u00ef\7m\2\2\u00ef&\3\2\2\2\u00f0\u00f1\7u\2\2\u00f1\u00f2"+
		"\7g\2\2\u00f2\u00f3\7c\2\2\u00f3\u00f4\7t\2\2\u00f4\u00f5\7e\2\2\u00f5"+
		"\u00f6\7j\2\2\u00f6(\3\2\2\2\u00f7\u00f8\7c\2\2\u00f8\u00f9\7u\2\2\u00f9"+
		"*\3\2\2\2\u00fa\u00fb\7f\2\2\u00fb\u00fc\7k\2\2\u00fc\u00fd\7u\2\2\u00fd"+
		"\u00fe\7r\2\2\u00fe\u00ff\7n\2\2\u00ff\u0100\7c\2\2\u0100\u0101\7{\2\2"+
		"\u0101,\3\2\2\2\u0102\u0103\7g\2\2\u0103\u0104\7z\2\2\u0104\u0105\7r\2"+
		"\2\u0105\u0106\7q\2\2\u0106\u0107\7t\2\2\u0107\u0108\7v\2\2\u0108.\3\2"+
		"\2\2\u0109\u010a\7t\2\2\u010a\u010b\7g\2\2\u010b\u010c\7v\2\2\u010c\u010d"+
		"\7w\2\2\u010d\u010e\7t\2\2\u010e\u010f\7p\2\2\u010f\60\3\2\2\2\u0110\u0111"+
		"\7u\2\2\u0111\u0112\7g\2\2\u0112\u0113\7n\2\2\u0113\u0114\7g\2\2\u0114"+
		"\u0115\7e\2\2\u0115\u0116\7v\2\2\u0116\62\3\2\2\2\u0117\u0118\7e\2\2\u0118"+
		"\u0119\7q\2\2\u0119\u011a\7p\2\2\u011a\u011b\7p\2\2\u011b\u011c\7g\2\2"+
		"\u011c\u011d\7e\2\2\u011d\u011e\7v\2\2\u011e\u011f\7f\2\2\u011f\u0120"+
		"\7d\2\2\u0120\64\3\2\2\2\u0121\u0122\7f\2\2\u0122\u0123\7d\2\2\u0123\66"+
		"\3\2\2\2\u0124\u0125\7y\2\2\u0125\u0126\7j\2\2\u0126\u0127\7g\2\2\u0127"+
		"\u0128\7t\2\2\u0128\u0129\7g\2\2\u01298\3\2\2\2\u012a\u012b\7k\2\2\u012b"+
		"\u012c\7p\2\2\u012c\u012d\7e\2\2\u012d\u012e\7n\2\2\u012e\u012f\7w\2\2"+
		"\u012f\u0130\7f\2\2\u0130\u0131\7g\2\2\u0131:\3\2\2\2\u0132\u0133\7g\2"+
		"\2\u0133\u0134\7z\2\2\u0134\u0135\7e\2\2\u0135\u0136\7n\2\2\u0136\u0137"+
		"\7w\2\2\u0137\u0138\7f\2\2\u0138\u0139\7g\2\2\u0139<\3\2\2\2\u013a\u013b"+
		"\7p\2\2\u013b\u013c\7q\2\2\u013c\u013d\7f\2\2\u013d\u013e\7g\2\2\u013e"+
		"\u013f\7u\2\2\u013f>\3\2\2\2\u0140\u0141\7g\2\2\u0141\u0142\7f\2\2\u0142"+
		"\u0143\7i\2\2\u0143\u0144\7g\2\2\u0144\u0145\7u\2\2\u0145@\3\2\2\2\u0146"+
		"\u0147\7n\2\2\u0147\u0148\7k\2\2\u0148\u0149\7o\2\2\u0149\u014a\7k\2\2"+
		"\u014a\u014b\7v\2\2\u014bB\3\2\2\2\u014c\u014d\7v\2\2\u014d\u014e\7k\2"+
		"\2\u014e\u014f\7o\2\2\u014f\u0150\7g\2\2\u0150D\3\2\2\2\u0151\u0152\7"+
		"u\2\2\u0152\u0153\7v\2\2\u0153\u0154\7g\2\2\u0154\u0155\7r\2\2\u0155F"+
		"\3\2\2\2\u0156\u0157\7o\2\2\u0157\u0158\7u\2\2\u0158H\3\2\2\2\u0159\u015a"+
		"\7u\2\2\u015aJ\3\2\2\2\u015b\u015c\7o\2\2\u015cL\3\2\2\2\u015d\u015e\7"+
		"v\2\2\u015e\u015f\7{\2\2\u015f\u0160\7r\2\2\u0160\u0161\7g\2\2\u0161N"+
		"\3\2\2\2\u0162\u0163\7p\2\2\u0163\u0164\7c\2\2\u0164\u0165\7o\2\2\u0165"+
		"\u0166\7g\2\2\u0166P\3\2\2\2\u0167\u0168\7r\2\2\u0168\u0169\7c\2\2\u0169"+
		"\u016a\7v\2\2\u016a\u016b\7j\2\2\u016bR\3\2\2\2\u016c\u016d\7f\2\2\u016d"+
		"\u016e\7u\2\2\u016e\u016f\7v\2\2\u016f\u0170\7k\2\2\u0170\u0171\7r\2\2"+
		"\u0171T\3\2\2\2\u0172\u0173\7f\2\2\u0173\u0174\7u\2\2\u0174\u0175\7v\2"+
		"\2\u0175\u0176\7r\2\2\u0176\u0177\7q\2\2\u0177\u0178\7t\2\2\u0178\u0179"+
		"\7v\2\2\u0179V\3\2\2\2\u017a\u017b\7u\2\2\u017b\u017c\7t\2\2\u017c\u017d"+
		"\7e\2\2\u017d\u017e\7k\2\2\u017e\u017f\7r\2\2\u017fX\3\2\2\2\u0180\u0181"+
		"\7u\2\2\u0181\u0182\7t\2\2\u0182\u0183\7e\2\2\u0183\u0184\7r\2\2\u0184"+
		"\u0185\7q\2\2\u0185\u0186\7t\2\2\u0186\u0187\7v\2\2\u0187Z\3\2\2\2\u0188"+
		"\u0189\7r\2\2\u0189\u018a\7k\2\2\u018a\u018b\7f\2\2\u018b\\\3\2\2\2\u018c"+
		"\u018d\7g\2\2\u018d\u018e\7z\2\2\u018e\u018f\7g\2\2\u018f\u0190\7p\2\2"+
		"\u0190\u0191\7c\2\2\u0191\u0192\7o\2\2\u0192\u0193\7g\2\2\u0193^\3\2\2"+
		"\2\u0194\u0195\7g\2\2\u0195\u0196\7z\2\2\u0196\u0197\7g\2\2\u0197\u0198"+
		"\7r\2\2\u0198\u0199\7c\2\2\u0199\u019a\7v\2\2\u019a\u019b\7j\2\2\u019b"+
		"`\3\2\2\2\u019c\u019d\7e\2\2\u019d\u019e\7o\2\2\u019e\u019f\7f\2\2\u019f"+
		"\u01a0\7n\2\2\u01a0\u01a1\7k\2\2\u01a1\u01a2\7p\2\2\u01a2\u01a3\7g\2\2"+
		"\u01a3b\3\2\2\2\u01a4\u01a5\7q\2\2\u01a5\u01a6\7r\2\2\u01a6\u01a7\7v\2"+
		"\2\u01a7\u01a8\7{\2\2\u01a8\u01a9\7r\2\2\u01a9\u01aa\7g\2\2\u01aad\3\2"+
		"\2\2\u01ab\u01ac\7k\2\2\u01ac\u01ad\7f\2\2\u01adf\3\2\2\2\u01ae\u01af"+
		"\7u\2\2\u01af\u01b0\7t\2\2\u01b0\u01b1\7e\2\2\u01b1\u01b2\7k\2\2\u01b2"+
		"\u01b3\7f\2\2\u01b3h\3\2\2\2\u01b4\u01b5\7f\2\2\u01b5\u01b6\7u\2\2\u01b6"+
		"\u01b7\7v\2\2\u01b7\u01b8\7k\2\2\u01b8\u01b9\7f\2\2\u01b9j\3\2\2\2\u01ba"+
		"\u01bb\7u\2\2\u01bb\u01bc\7v\2\2\u01bc\u01bd\7c\2\2\u01bd\u01be\7t\2\2"+
		"\u01be\u01bf\7v\2\2\u01bf\u01c0\7v\2\2\u01c0\u01c1\7k\2\2\u01c1\u01c2"+
		"\7o\2\2\u01c2\u01c3\7g\2\2\u01c3l\3\2\2\2\u01c4\u01c5\7g\2\2\u01c5\u01c6"+
		"\7p\2\2\u01c6\u01c7\7f\2\2\u01c7\u01c8\7v\2\2\u01c8\u01c9\7k\2\2\u01c9"+
		"\u01ca\7o\2\2\u01ca\u01cb\7g\2\2\u01cbn\3\2\2\2\u01cc\u01cd\7c\2\2\u01cd"+
		"\u01ce\7o\2\2\u01ce\u01cf\7q\2\2\u01cf\u01d0\7w\2\2\u01d0\u01d1\7p\2\2"+
		"\u01d1\u01d2\7v\2\2\u01d2p\3\2\2\2\u01d3\u01d4\7r\2\2\u01d4\u01d5\7t\2"+
		"\2\u01d5\u01d6\7q\2\2\u01d6\u01d7\7e\2\2\u01d7\u01d8\7g\2\2\u01d8\u01d9"+
		"\7u\2\2\u01d9\u01da\7u\2\2\u01dar\3\2\2\2\u01db\u01dc\7h\2\2\u01dc\u01dd"+
		"\7k\2\2\u01dd\u01de\7n\2\2\u01de\u01df\7g\2\2\u01dft\3\2\2\2\u01e0\u01e1"+
		"\7p\2\2\u01e1\u01e2\7g\2\2\u01e2\u01e3\7v\2\2\u01e3\u01e4\7y\2\2\u01e4"+
		"\u01e5\7q\2\2\u01e5\u01e6\7t\2\2\u01e6\u01e7\7m\2\2\u01e7v\3\2\2\2\u01e8"+
		"\u01e9\7t\2\2\u01e9\u01ea\7g\2\2\u01ea\u01eb\7c\2\2\u01eb\u01ec\7f\2\2"+
		"\u01ecx\3\2\2\2\u01ed\u01ee\7y\2\2\u01ee\u01ef\7t\2\2\u01ef\u01f0\7k\2"+
		"\2\u01f0\u01f1\7v\2\2\u01f1\u01f2\7g\2\2\u01f2z\3\2\2\2\u01f3\u01f4\7"+
		"g\2\2\u01f4\u01f5\7z\2\2\u01f5\u01f6\7g\2\2\u01f6\u01f7\7e\2\2\u01f7\u01f8"+
		"\7x\2\2\u01f8\u01f9\7g\2\2\u01f9|\3\2\2\2\u01fa\u01fb\7p\2\2\u01fb\u01fc"+
		"\7w\2\2\u01fc\u01fd\7n\2\2\u01fd\u01fe\7n\2\2\u01fe~\3\2\2\2\u01ff\u0200"+
		"\7/\2\2\u0200\u0201\7@\2\2\u0201\u0080\3\2\2\2\u0202\u0203\7?\2\2\u0203"+
		"\u0082\3\2\2\2\u0204\u0205\7#\2\2\u0205\u0206\7?\2\2\u0206\u0084\3\2\2"+
		"\2\u0207\u0208\7@\2\2\u0208\u0086\3\2\2\2\u0209\u020a\7@\2\2\u020a\u020b"+
		"\7?\2\2\u020b\u0088\3\2\2\2\u020c\u020d\7>\2\2\u020d\u008a\3\2\2\2\u020e"+
		"\u020f\7>\2\2\u020f\u0210\7?\2\2\u0210\u008c\3\2\2\2\u0211\u0212\7n\2"+
		"\2\u0212\u0213\7k\2\2\u0213\u0214\7m\2\2\u0214\u0215\7g\2\2\u0215\u008e"+
		"\3\2\2\2\u0216\u0217\7~\2\2\u0217\u0090\3\2\2\2\u0218\u0219\7(\2\2\u0219"+
		"\u0092\3\2\2\2\u021a\u021b\7/\2\2\u021b\u0094\3\2\2\2\u021c\u021d\7(\2"+
		"\2\u021d\u021e\7(\2\2\u021e\u0096\3\2\2\2\u021f\u0220\7~\2\2\u0220\u0221"+
		"\7~\2\2\u0221\u0098\3\2\2\2\u0222\u0225\5\u009bN\2\u0223\u0225\5\u009d"+
		"O\2\u0224\u0222\3\2\2\2\u0224\u0223\3\2\2\2\u0225\u009a\3\2\2\2\u0226"+
		"\u0228\5\u00a5S\2\u0227\u0226\3\2\2\2\u0228\u0229\3\2\2\2\u0229\u0227"+
		"\3\2\2\2\u0229\u022a\3\2\2\2\u022a\u009c\3\2\2\2\u022b\u022d\7/\2\2\u022c"+
		"\u022e\5\u00a5S\2\u022d\u022c\3\2\2\2\u022e\u022f\3\2\2\2\u022f\u022d"+
		"\3\2\2\2\u022f\u0230\3\2\2\2\u0230\u009e\3\2\2\2\u0231\u0235\7$\2\2\u0232"+
		"\u0234\5\u00a1Q\2\u0233\u0232\3\2\2\2\u0234\u0237\3\2\2\2\u0235\u0233"+
		"\3\2\2\2\u0235\u0236\3\2\2\2\u0236\u0238\3\2\2\2\u0237\u0235\3\2\2\2\u0238"+
		"\u0242\7$\2\2\u0239\u023d\7)\2\2\u023a\u023c\5\u00a3R\2\u023b\u023a\3"+
		"\2\2\2\u023c\u023f\3\2\2\2\u023d\u023b\3\2\2\2\u023d\u023e\3\2\2\2\u023e"+
		"\u0240\3\2\2\2\u023f\u023d\3\2\2\2\u0240\u0242\7)\2\2\u0241\u0231\3\2"+
		"\2\2\u0241\u0239\3\2\2\2\u0242\u00a0\3\2\2\2\u0243\u0244\n\2\2\2\u0244"+
		"\u00a2\3\2\2\2\u0245\u0246\n\3\2\2\u0246\u00a4\3\2\2\2\u0247\u0248\t\4"+
		"\2\2\u0248\u00a6\3\2\2\2\u0249\u024e\5\u00a9U\2\u024a\u024d\5\u00a9U\2"+
		"\u024b\u024d\5\u00a5S\2\u024c\u024a\3\2\2\2\u024c\u024b\3\2\2\2\u024d"+
		"\u0250\3\2\2\2\u024e\u024c\3\2\2\2\u024e\u024f\3\2\2\2\u024f\u00a8\3\2"+
		"\2\2\u0250\u024e\3\2\2\2\u0251\u0252\t\5\2\2\u0252\u00aa\3\2\2\2\u0253"+
		"\u0255\t\6\2\2\u0254\u0253\3\2\2\2\u0255\u0256\3\2\2\2\u0256\u0254\3\2"+
		"\2\2\u0256\u0257\3\2\2\2\u0257\u0258\3\2\2\2\u0258\u0259\bV\2\2\u0259"+
		"\u00ac\3\2\2\2\u025a\u025c\t\7\2\2\u025b\u025a\3\2\2\2\u025c\u025d\3\2"+
		"\2\2\u025d\u025b\3\2\2\2\u025d\u025e\3\2\2\2\u025e\u025f\3\2\2\2\u025f"+
		"\u0260\bW\2\2\u0260\u00ae\3\2\2\2\u0261\u0262\7\61\2\2\u0262\u0263\7,"+
		"\2\2\u0263\u0267\3\2\2\2\u0264\u0266\13\2\2\2\u0265\u0264\3\2\2\2\u0266"+
		"\u0269\3\2\2\2\u0267\u0268\3\2\2\2\u0267\u0265\3\2\2\2\u0268\u026a\3\2"+
		"\2\2\u0269\u0267\3\2\2\2\u026a\u026b\7,\2\2\u026b\u026c\7\61\2\2\u026c"+
		"\u026d\3\2\2\2\u026d\u026e\bX\2\2\u026e\u00b0\3\2\2\2\u026f\u0270\7\61"+
		"\2\2\u0270\u0271\7\61\2\2\u0271\u0275\3\2\2\2\u0272\u0274\13\2\2\2\u0273"+
		"\u0272\3\2\2\2\u0274\u0277\3\2\2\2\u0275\u0276\3\2\2\2\u0275\u0273\3\2"+
		"\2\2\u0276\u0279\3\2\2\2\u0277\u0275\3\2\2\2\u0278\u027a\7\17\2\2\u0279"+
		"\u0278\3\2\2\2\u0279\u027a\3\2\2\2\u027a\u027b\3\2\2\2\u027b\u027c\7\f"+
		"\2\2\u027c\u027d\3\2\2\2\u027d\u027e\bY\2\2\u027e\u00b2\3\2\2\2\20\2\u0224"+
		"\u0229\u022f\u0235\u023d\u0241\u024c\u024e\u0256\u025d\u0267\u0275\u0279"+
		"\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}