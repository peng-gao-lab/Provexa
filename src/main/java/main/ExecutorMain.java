package main;

import executor.ExecutionContext;
import executor.QueryStatement;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.neo4j.fabric.planning.Fragment;
import query.parser.QueryLexer;
import query.parser.QueryParser;
import query.backtracking.BackTrackConstraints;

import java.util.LinkedList;

public class ExecutorMain {
    public static void main(String[] args) throws Exception {
        Options options = new Options();
        options.addOption(Option.builder("r").longOpt("recursive").build());
        options.addOption(Option.builder("i").longOpt("ignoreConstraints").build());
        options.addOption(Option.builder("n").longOpt("neo4j").build());
        options.addOption(Option.builder("d").longOpt("disablePopulate").build());
        DefaultParser clParser = new DefaultParser();
        CommandLine cmdLine = clParser.parse(options, args);
        args = cmdLine.getArgs();
        String inputFile = args.length > 0 ? args[0] : "input/query_search.txt";
        CharStream inputStream = CharStreams.fromFileName(inputFile);
        ExecutionContext ctx = new ExecutionContext();
        if (cmdLine.hasOption("r")) {
            ctx.setUseRecursive(true);
        }
        if (cmdLine.hasOption("i")) {
            ctx.setIgnoreConstraints(true);
        }
        if (cmdLine.hasOption("n")) {
            ExecutionContext.setDbType(ExecutionContext.DbType.Neo4j);
        } else {
            ExecutionContext.setDbType(ExecutionContext.DbType.Postgres);
        }
        if (cmdLine.hasOption("d")) {
            ExecutionContext.setIfPopulate(false);
        }
        ctx.setVerbose(true);
        QueryLexer lexer = new QueryLexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        QueryParser parser = new QueryParser(tokens, ctx);
        LinkedList<QueryStatement> stmts = parser.start().stmts;
        // for (QueryStatement stmt : stmts) {
        //     System.out.println(stmt.toString());
        // }
        try {
            for (QueryStatement stmt : stmts) {
                stmt.execute();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
