package main;

import java.util.Collections;
import java.util.LinkedList;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import executor.ExecutionContext;
import executor.QueryStatement;
import executor.WebResponse;
import query.parser.QueryLexer;
import query.parser.QueryParser;

@CrossOrigin(origins = "http://localhost:3000")
@SpringBootApplication
@RestController
public class WebMain {
    private static ExecutionContext ctx;

    public static void init(String port, String mode, String dbType) {
        if (ctx == null) {
            ctx = new ExecutionContext();
            ctx.enableWebContext();
            ctx.setVerbose(true);
            if (mode.equalsIgnoreCase("r")) {
                ctx.setUseRecursive(true);
            }
            
            // Set database type based on parameter
            if (dbType.equalsIgnoreCase("neo4j")) {
                ExecutionContext.setDbType(ExecutionContext.DbType.Neo4j);
            } else {
                ExecutionContext.setDbType(ExecutionContext.DbType.Postgres);
            }
            
            SpringApplication app = new SpringApplication(WebMain.class);
            app.setDefaultProperties(Collections.singletonMap("server.port", port));
            app.run();
        }
    }

    public static void main(String[] args) {
        String port = "8080";
        String mode = "o"; // o (optimized) or r (recursive)
        String dbType = "postgres"; // postgres or neo4j
        
        // Parse named arguments
        for (String arg : args) {
            if (arg.startsWith("--port=")) {
                port = arg.substring(7);
            } else if (arg.startsWith("--mode=")) {
                mode = arg.substring(7);
            } else if (arg.startsWith("--db=")) {
                dbType = arg.substring(5);
            } else if (arg.equals("--help") || arg.equals("-h")) {
                printUsage();
                return;
            }
        }
        
        init(port, mode, dbType);
    }
    
    private static void printUsage() {
        System.out.println("Usage: mvn exec:java -Dexec.mainClass=main.WebMain -Dexec.args=\"[options]\"");
        System.out.println("Options:");
        System.out.println("  --port=<port>     Set the server port (default: 8080)");
        System.out.println("  --mode=<mode>     Set execution mode: 'o' for optimized, 'r' for recursive (default: o)");
        System.out.println("  --db=<database>   Set database type: 'postgres' or 'neo4j' (default: postgres)");
        System.out.println("  --help, -h        Show this help message");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  mvn exec:java -Dexec.mainClass=main.WebMain -Dexec.args=\"--port=9090 --mode=r --db=neo4j\"");
        System.out.println("  mvn exec:java -Dexec.mainClass=main.WebMain -Dexec.args=\"--port=8080\"");
        System.out.println("  mvn exec:java -Dexec.mainClass=main.WebMain -Dexec.args=\"--db=neo4j\"");
    }
    @PostMapping("/execute")
    public WebResponse execute(@RequestBody String input) {
        try {
            CharStream inputStream = CharStreams.fromString(input);
            QueryLexer lexer = new QueryLexer(inputStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            QueryParser parser = new QueryParser(tokens, ctx);
            LinkedList<QueryStatement> stmts = parser.start().stmts;
            for (QueryStatement stmt : stmts) {
                stmt.execute();
            }
        } catch (Exception ex) {
            ctx.log(ex.toString());
        }
        return ctx.dumpResponse();
    }
}
