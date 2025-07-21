package main;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import executor.ExecutionContext;
import executor.QueryStatement;
import query.parser.QueryLexer;
import query.parser.QueryParser;

public class ExecutorMain {
    public static void main(String[] args) throws Exception {
        String inputFile = "examples/no-web-ui/input_query.txt";
        boolean useRecursive = false;
        boolean ignoreConstraints = false;
        boolean useNeo4j = false;
        boolean disablePopulate = false;

        // Parse named arguments
        for (String arg : args) {
            if (arg.startsWith("--inputFile=")) {
                inputFile = arg.substring(12);
            } else if (arg.equals("--neo4j")) {
                useNeo4j = true;
            } else if (arg.equals("--help") || arg.equals("-h")) {
                printUsage();
                return;
            }
        }

        // Validate input file
        if (!Files.exists(Paths.get(inputFile)) || !Files.isReadable(Paths.get(inputFile))) {
            System.err.println("Error: Input file does not exist or is not readable: " + inputFile);
            System.exit(1);
            return;
        }

        // Initialize ExecutionContext
        ExecutionContext ctx = new ExecutionContext();
        ctx.setUseRecursive(useRecursive);
        ctx.setIgnoreConstraints(ignoreConstraints);
        ExecutionContext.setDbType(useNeo4j ? ExecutionContext.DbType.Neo4j : ExecutionContext.DbType.Postgres);
        ExecutionContext.setIfPopulate(!disablePopulate);
        ctx.setVerbose(true);

        // Parse and execute query
        CharStream inputStream = CharStreams.fromFileName(inputFile);
        QueryLexer lexer = new QueryLexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        QueryParser parser = new QueryParser(tokens, ctx);
        LinkedList<QueryStatement> stmts = parser.start().stmts;

        try {
            for (QueryStatement stmt : stmts) {
                stmt.execute();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void printUsage() {
        System.out.println("Usage: mvn -q exec:java -Dexec.mainClass=main.ExecutorMain -Dexec.args=\"[options]\"");
        System.out.println("Options:");
        System.out.println("  --inputFile=<path>       Path to the input query file (default: examples/no-web-ui/input_query.txt)");
        System.out.println("  --neo4j                  Use Neo4j database");
        System.out.println("  --help, -h               Show this help message");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  mvn -q exec:java -Dexec.mainClass=main.ExecutorMain -Dexec.args=\"--inputFile=input_query.txt\"");
        System.out.println("  mvn -q exec:java -Dexec.mainClass=main.ExecutorMain -Dexec.args=\"--neo4j --inputFile=input_query.txt\"");
    }
}
