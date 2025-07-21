package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import datamodel.EventEdge;
import datamodel.FileEntity;
import datamodel.NetworkEntity;
import datamodel.ProcessEntity;
import db.Postgres;
import db.Neo4j;
import graph.GlobalDependencyGraph;
import parser.sysdigparser.ProcessOriginalParserOutput;

public class SysdigMain {

    public static void main(String[] args) throws Exception {
        String logFile = "examples/sysdig/multistep_supply_chain/multistep_supply_chain.txt";
        String dbType = "postgres"; // postgres or neo4j
        String dbName = "testing"; // database name for neo4j
        
        // Parse named arguments
        for (String arg : args) {
            if (arg.startsWith("--logfile=")) {
                logFile = arg.substring(10);
            } else if (arg.startsWith("--db=")) {
                dbType = arg.substring(5);
            } else if (arg.startsWith("--dbname=")) {
                dbName = arg.substring(9);
            } else if (arg.equals("--help") || arg.equals("-h")) {
                printUsage();
                return;
            }
        }

        System.out.println("#################SysdigDBMain start...#################");
        System.out.println("Log file: " + logFile);
        System.out.println("Database type: " + dbType);
        if (dbType.equalsIgnoreCase("neo4j")) {
            System.out.println("Database name: " + dbName);
        }
        ProcessOriginalParserOutput parserOutput = new ProcessOriginalParserOutput(logFile);
        Map<String, FileEntity> fileMap = parserOutput.getFileMap(); // str representation of file: file path
        Map<String, NetworkEntity> networkMap = parserOutput.getNetworkMap(); // str representation of process: pid + procname
        Map<String, ProcessEntity> processMap = parserOutput.getProcessMap(); // str representation of network: ip -> ip
        List<EventEdge> processFileLst = new ArrayList<>();
        List<EventEdge> processNetworkLst = new ArrayList<>();
        List<EventEdge> processProcessLst = new ArrayList<>();

        GlobalDependencyGraph graph = new GlobalDependencyGraph(parserOutput);
        graph.GenerateGraph();

        // mergeEdgeFallInTheRange(1, graph.getJg(), processFileLst, processNetworkLst, processProcessLst);
        for (EventEdge e : graph.getJg().edgeSet()) {
            if (e.getType().equals("PtoF") || e.getType().equals("FtoP")) {
                processFileLst.add(e);
            } else if(e.getType().equals("PtoN") ||e.getType().equals("NtoP")) {
                processNetworkLst.add(e);
            } else if(e.getType().equals("PtoP")) {
                processProcessLst.add(e);
            }
        }

        // Generate database based on type
        if (dbType.equalsIgnoreCase("neo4j")) {
            System.out.println("---------------------Start to generate neo4j DB...");
            Neo4j neo4jDb = new Neo4j(fileMap, networkMap, processMap, processFileLst, processNetworkLst, processProcessLst);
            try {
                neo4jDb.createDb(dbName);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            System.out.println("---------------------Start to generate postgres DB...");
            Postgres postgres = new Postgres(fileMap, networkMap, processMap, processFileLst, processNetworkLst, processProcessLst);
            postgres.createDb();
        }
    }
    
    private static void printUsage() {
        System.out.println("Usage: mvn exec:java -Dexec.mainClass=main.SysdigMain -Dexec.args=\"[options]\"");
        System.out.println("Options:");
        System.out.println("  --logfile=<path>  Path to the sysdig log file to process");
        System.out.println("                    (default: examples/sysdig/multistep_supply_chain/multistep_supply_chain.txt)");
        System.out.println("  --db=<database>   Set database type: 'postgres' or 'neo4j' (default: postgres)");
        System.out.println("  --dbname=<name>   Database name for Neo4j (default: testing, only used with --db=neo4j)");
        System.out.println("  --help, -h        Show this help message");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  mvn exec:java -Dexec.mainClass=main.SysdigMain -Dexec.args=\"--logfile=examples/sysdig/malicious_ssh_theft/malicious_ssh_theft.txt --db=neo4j --dbname=investigation\"");
        System.out.println("  mvn exec:java -Dexec.mainClass=main.SysdigMain -Dexec.args=\"--logfile=my_logs.txt\"");
        System.out.println("  mvn exec:java -Dexec.mainClass=main.SysdigMain -Dexec.args=\"--db=neo4j\"");
    }
}
