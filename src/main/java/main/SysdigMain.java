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
        String logFile = args.length > 0 ? args[0] : "examples/sysdig/multistep_supply_chain/multistep_supply_chain.txt";

        System.out.println("#################SysdigDBMain start...#################");
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

        System.out.println("---------------------Start to generate postgres DB...");
        Postgres postgres = new Postgres(fileMap, networkMap, processMap, processFileLst, processNetworkLst, processProcessLst);
        postgres.createDb();

        //Generate Neo4j database
//	    System.out.println("---------------------Start to generate neo4j DB...");
//  		Neo4j neo4jDb = new Neo4j(fileMap, networkMap, processMap, processFileLst, processNetworkLst, processProcessLst);
//          try {
//          	neo4jDb.createDb("dropbox");
//  		} catch (IOException e1) {
//  			e1.printStackTrace();
//  		}
    }
}
