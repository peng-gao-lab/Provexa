package db;

import datamodel.*;
import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.schema.IndexCreator;
import org.neo4j.graphdb.schema.IndexDefinition;
import org.neo4j.graphdb.schema.Schema;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Neo4j {
	private Map<String, FileEntity> fMap;
	private Map<String, NetworkEntity> nMap;
	private Map<String, ProcessEntity> pMap;
	private Map<String, RegistryEntity> rMap = null;
	private List<EventEdge> processFileLst;
	private List<EventEdge> processNetworkLst;
	private List<EventEdge> processProcessLst;
	private List<EventEdge> processRegistryLst = null;

	private final int BATCH_SIZE = 100;

	public Neo4j() {}

	// Initiator without registry
	public Neo4j(Map<String, FileEntity> fileMap, Map<String, NetworkEntity> networkMap,
				 Map<String,ProcessEntity> processMap,
				 List<EventEdge> processFileLst, List<EventEdge> processNetworkLst,
				 List<EventEdge> processProcessLst) {
		this.fMap = fileMap;
		this.nMap = networkMap;
		this.pMap = processMap;
		this.processFileLst = processFileLst;
		this.processNetworkLst = processNetworkLst;
		this.processProcessLst = processProcessLst;
	}

	// Initiator with registry
	public Neo4j(Map<String, FileEntity> fileMap, Map<String, NetworkEntity> networkMap,
				 Map<String,ProcessEntity> processMap, Map<String, RegistryEntity> registerMap,
				 List<EventEdge> processFileLst, List<EventEdge> processNetworkLst,
				 List<EventEdge> processProcessLst, List<EventEdge> processRegistryLst) {
		this.fMap = fileMap;
		this.nMap = networkMap;
		this.pMap = processMap;
		this.rMap = registerMap;
		this.processFileLst = processFileLst;
		this.processNetworkLst = processNetworkLst;
		this.processProcessLst = processProcessLst;
		this.processRegistryLst = processRegistryLst;
	}

    public void createDb(String dbName) throws IOException {
		DatabaseManagementService managementService = Neo4jUtil.connectDb(dbName);
		GraphDatabaseService graphDb = managementService.database(dbName);
		// Create indexes
		createIndex(graphDb);
		batchInsertFileNodes(this.fMap, graphDb);
		batchInsertNetworkNodes(this.nMap, graphDb);
		batchInsertProcessNodes(this.pMap, graphDb);
		if (this.rMap != null) {
			batchInsertRegistryNodes(this.rMap, graphDb);
		}
		batchInsertFileEvents(this.processFileLst, graphDb);
		batchInsertNetworkEvents(this.processNetworkLst, graphDb);
		batchInsertProcessEvents(this.processProcessLst, graphDb);
		if (this.processRegistryLst != null) {
			batchInsertRegistryEvents(this.processRegistryLst, graphDb);
		}
		// Print nodes and edges number.
		databaseCount(dbName, graphDb);
		Neo4jUtil.shutDown(managementService);
	}

	public void databaseCount(String dbName, GraphDatabaseService graphDb) {
		try(Transaction tx = graphDb.beginTx()) {
			Long nodeCount=0L, edgeCount=0L;
			String query = "MATCH (n) RETURN COUNT(n) AS c";
			Result result = tx.execute(query);
			if (result.hasNext()) {
				Map<String, Object> row = result.next();
				nodeCount = (Long) row.get("c");
			}
			query = "MATCH ()-[]->() RETURN COUNT(*) AS c";
			result = tx.execute(query);
			if (result.hasNext()) {
				Map<String, Object> row = result.next();
				edgeCount = (Long) row.get("c");
			}
			tx.commit();
			System.out.printf("%s database has %s nodes, %s edges\n", dbName, nodeCount, edgeCount);
		}
	}
    
    
    public enum LogsAnalyzerLabels implements Label {
    	File, Process, Network, Registry
    }
    
    public enum LogsAnalyzerRelationshipTypes implements RelationshipType {
    	FileEvent, ProcessEvent, NetworkEvent, RegistryEvent
    }
    
    public int batchInsertFileNodes(Map<String, FileEntity> fMap, GraphDatabaseService graphDb) throws IOException {
		List<FileEntity> fLst = new ArrayList<>(fMap.values());
		Transaction tx = graphDb.beginTx();
//		Date startDate = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String start = sdf.format(startDate);
//		System.out.println("Batch insert process start::::::::::::::::"+start);
		for(int j=0; j<fLst.size(); j++) {
			FileEntity f = fLst.get(j);
			Node node = tx.createNode();
			node.addLabel(LogsAnalyzerLabels.File);
			node.setProperty("id", f.getUniqID());
			// Add type
			node.setProperty("type", "file");
			node.setProperty("ownergroupid", f.getGroup_ID()==null?"":f.getGroup_ID());
			node.setProperty("hostname", f.getHost());
			node.setProperty("name", f.getName());
			node.setProperty("path", f.getPath()==null?"":f.getPath());
			node.setProperty("owneruserid", f.getUser_ID()==null?"":f.getUser_ID());

			if (j % BATCH_SIZE == 0) {
				tx.commit();
				tx.close();
				tx = graphDb.beginTx();
			}
		}
		tx.commit();
//		Date endDate = new Date();
//		String end = sdf.format(endDate);
//		System.out.println("Batch insert process end::::::::::::::::"+end);
    	return fLst.size();
	}
    
    public int batchInsertProcessNodes(Map<String, ProcessEntity> pMap, GraphDatabaseService graphDb) throws IOException {
		List<ProcessEntity> pLst = new ArrayList<>(pMap.values());
		Transaction tx = graphDb.beginTx();
	    for(int j=0; j<pLst.size(); j++) {
	        ProcessEntity p = pLst.get(j);
	        Node node = tx.createNode();
	        node.addLabel(LogsAnalyzerLabels.Process);
	        node.setProperty("id", p.getUniqID());
			// Add type
			node.setProperty("type", "process");
	        node.setProperty("exename", p.getName());
			node.setProperty("exepath", p.getExePath());
	        node.setProperty("ownergroupid", p.getGroupID()==null?"":p.getGroupID());
	        node.setProperty("hostname", p.getHost());
	        node.setProperty("pid", Long.parseLong(p.getPid())); // Convert pid's type to long from String
	        node.setProperty("owneruserid", p.getUid()==null?"":p.getUid());
			node.setProperty("cmdline", p.getCmdLine()==null?"":p.getCmdLine());
	        
	        if (j % BATCH_SIZE == 0) {
	        	tx.commit();
	        	tx.close();
	        	tx = graphDb.beginTx();
	        }
	    }
	    tx.commit();
        return pLst.size();
	}
    
    public int batchInsertNetworkNodes(Map<String, NetworkEntity> nMap, GraphDatabaseService graphDb) throws IOException {
		List<NetworkEntity> nLst = new ArrayList<>(nMap.values());
		Transaction tx = graphDb.beginTx();
	    for(int j=0; j<nLst.size(); j++) {
	    	NetworkEntity n = nLst.get(j);
	        Node node = tx.createNode();
	        node.addLabel(LogsAnalyzerLabels.Network);
	        node.setProperty("id", n.getUniqID());
			// Add type
			node.setProperty("type", "network");
	        node.setProperty("dstip", n.getDstAddress());
	        node.setProperty("dstport", Integer.parseInt(n.getDstPort()));
	        node.setProperty("hostname", n.getHost());
	        node.setProperty("srcip", n.getSrcAddress());
	        node.setProperty("srcport", Integer.parseInt(n.getSrcPort()));
	        
	        if (j % BATCH_SIZE == 0) {
	        	tx.commit();
	        	tx.close();
	        	tx = graphDb.beginTx();
	        }
	    }
	    tx.commit();
        return nLst.size();
	}

	public int batchInsertRegistryNodes(Map<String, RegistryEntity> rMap, GraphDatabaseService graphDb) throws IOException {
		List<RegistryEntity> rLst = new ArrayList<>(rMap.values());
		Transaction tx = graphDb.beginTx();
		for(int j=0; j<rLst.size(); j++) {
			RegistryEntity r = rLst.get(j);
			Node node = tx.createNode();
			node.addLabel(LogsAnalyzerLabels.Registry);
			node.setProperty("id", r.getUniqID());
			// Add type
			node.setProperty("type", "registry");
			node.setProperty("hostname", r.getHost());
			node.setProperty("registrypath", r.getPath());

			if (j % BATCH_SIZE == 0) {
				tx.commit();
				tx.close();
				tx = graphDb.beginTx();
			}
		}
		tx.commit();
		return rLst.size();
	}
    
    public String batchInsertFileEvents(List<EventEdge> processFileLst, GraphDatabaseService graphDb) throws IOException {
		Transaction tx = graphDb.beginTx();
		int count = 0;
	    for(int j=0; j<processFileLst.size(); j++) {
	    	EventEdge e = processFileLst.get(j);
			Node startNode = tx.findNode(LogsAnalyzerLabels.File, "id", e.getSource().getID());
			Node endNode = tx.findNode(LogsAnalyzerLabels.Process, "id", e.getSink().getID());
			if (startNode == null || endNode == null) {
				startNode = tx.findNode(LogsAnalyzerLabels.Process, "id", e.getSource().getID());
				endNode = tx.findNode(LogsAnalyzerLabels.File, "id", e.getSink().getID());
			}
			if (startNode == null || endNode == null) {
				count++;
				continue;
			}
			Relationship relationship = startNode.createRelationshipTo(endNode, LogsAnalyzerRelationshipTypes.FileEvent);
			relationship.setProperty("id", e.getID());
			relationship.setProperty("starttime", e.getStartNs());
			relationship.setProperty("endtime", e.getEndNs());
			relationship.setProperty("srcid", e.getSource().getID());
			relationship.setProperty("dstid", e.getSink().getID());
			relationship.setProperty("optype", e.getEvent());
			relationship.setProperty("hostname", "RiseLab");
			relationship.setProperty("eventno", e.getEventNo());
			relationship.setProperty("amount", e.getSize());
	      
			if (j % BATCH_SIZE == 0) {
				tx.commit();
				tx.close();
				tx = graphDb.beginTx();
			}
	    }
        if (count > 0) {
//            System.out.printf("%s nodes not found%n", count);
        }
	    tx.commit();
        return "Done!!!";
	}
    
    public String batchInsertNetworkEvents(List<EventEdge> processNetworkLst, GraphDatabaseService graphDb) throws IOException {
		Transaction tx = graphDb.beginTx();
	    for(int j=0; j<processNetworkLst.size(); j++) {
	    	EventEdge e = processNetworkLst.get(j);
			Node startNode = tx.findNode(LogsAnalyzerLabels.Process, "id", e.getSource().getID());
			Node endNode = tx.findNode(LogsAnalyzerLabels.Network, "id", e.getSink().getID());
			if (startNode == null || endNode == null) {
				startNode = tx.findNode(LogsAnalyzerLabels.Network, "id", e.getSource().getID());
				endNode = tx.findNode(LogsAnalyzerLabels.Process, "id", e.getSink().getID());
			}
			if (startNode == null || endNode == null) {
				continue;
			}
			Relationship relationship = startNode.createRelationshipTo(endNode, LogsAnalyzerRelationshipTypes.NetworkEvent);
			relationship.setProperty("id", e.getID());
			relationship.setProperty("starttime", e.getStartNs());
			relationship.setProperty("endtime", e.getEndNs());
			relationship.setProperty("srcid", e.getSource().getID());
			relationship.setProperty("dstid", e.getSink().getID());
			relationship.setProperty("optype", e.getEvent());
			relationship.setProperty("hostname", "RiseLab");
			relationship.setProperty("amount", e.getSize());
			relationship.setProperty("eventno", e.getEventNo());
	      
			if (j % BATCH_SIZE == 0) {
				tx.commit();
				tx.close();
				tx = graphDb.beginTx();
			}
	    }
	    tx.commit();
        return "Done!!!";
	}
    
    public String batchInsertProcessEvents(List<EventEdge> processProcessLst, GraphDatabaseService graphDb) throws IOException {
		Transaction tx = graphDb.beginTx();
	    for(int j=0; j<processProcessLst.size(); j++) {
	    	EventEdge e = processProcessLst.get(j);
			Node startNode = tx.findNode(LogsAnalyzerLabels.Process, "id", e.getSource().getID());
			Node endNode = tx.findNode(LogsAnalyzerLabels.Process, "id", e.getSink().getID());
			if (startNode == null || endNode == null) {
				continue;
			}
			Relationship relationship = startNode.createRelationshipTo(endNode, LogsAnalyzerRelationshipTypes.ProcessEvent);
			relationship.setProperty("id", e.getID());
			relationship.setProperty("starttime", e.getStartNs());
			relationship.setProperty("endtime", e.getEndNs());
			relationship.setProperty("srcid", e.getSource().getID());
			relationship.setProperty("dstid", e.getSink().getID());
			relationship.setProperty("optype", e.getEvent());
			relationship.setProperty("hostname", "RiseLab");
			relationship.setProperty("amount", e.getSize());
			relationship.setProperty("eventno", e.getEventNo());
	      
			if (j % BATCH_SIZE == 0) {
				tx.commit();
				tx.close();
				tx = graphDb.beginTx();
			}
	    }
	    tx.commit();
        return "Done!!!";
	}

	public String batchInsertRegistryEvents(List<EventEdge> processRegistryLst, GraphDatabaseService graphDb) throws IOException {
		Transaction tx = graphDb.beginTx();
		for(int j=0; j<processRegistryLst.size(); j++) {
			EventEdge e = processRegistryLst.get(j);
			Node startNode = tx.findNode(LogsAnalyzerLabels.Process, "id", e.getSource().getID());
			Node endNode = tx.findNode(LogsAnalyzerLabels.Registry, "id", e.getSink().getID());
			if (startNode == null || endNode == null) {
				continue;
			}
			Relationship relationship = startNode.createRelationshipTo(endNode, LogsAnalyzerRelationshipTypes.RegistryEvent);
			relationship.setProperty("id", e.getID());
			relationship.setProperty("starttime", e.getStartNs());
			relationship.setProperty("endtime", e.getEndNs());
			relationship.setProperty("srcid", e.getSource().getID());
			relationship.setProperty("dstid", e.getSink().getID());
			relationship.setProperty("optype", e.getEvent());
			relationship.setProperty("hostname", "RiseLab");
			relationship.setProperty("amount", e.getSize());
			relationship.setProperty("eventno", e.getEventNo());

			if (j % BATCH_SIZE == 0) {
				tx.commit();
				tx.close();
				tx = graphDb.beginTx();
			}
		}
		tx.commit();
		return "Done!!!";
	}

	public void createIndex(GraphDatabaseService graphDb) {
		// Create node indexes (align with PostgreSQL)
		createNodeIndex(graphDb, LogsAnalyzerLabels.File, Arrays.asList("id", "name", "path"));
		createNodeIndex(graphDb, LogsAnalyzerLabels.Process, Arrays.asList("id", "exename", "exepath"));
		createNodeIndex(graphDb, LogsAnalyzerLabels.Network, Arrays.asList("id", "dstip", "srcip"));
		createNodeIndex(graphDb, LogsAnalyzerLabels.Registry, Arrays.asList("id", "regirtrypath"));
		// Create relation indexes (align with PostgreSQL)
		createEdgeIndex(graphDb, LogsAnalyzerRelationshipTypes.FileEvent, Arrays.asList("id", "dstid", "srcid"));
		createEdgeIndex(graphDb, LogsAnalyzerRelationshipTypes.ProcessEvent, Arrays.asList("id", "dstid", "srcid"));
		createEdgeIndex(graphDb, LogsAnalyzerRelationshipTypes.NetworkEvent, Arrays.asList("id", "dstid", "srcid"));
		createEdgeIndex(graphDb, LogsAnalyzerRelationshipTypes.RegistryEvent, Arrays.asList("id", "dstid", "srcid"));
	}
	// https://neo4j.com/docs/java-reference/4.3/java-embedded/indexes/
	private void createNodeIndex(GraphDatabaseService graphDb, Label label, List<String> property) {
		IndexDefinition idxDef;
		IndexCreator idxCreator;
		try (Transaction tx = graphDb.beginTx()){
			Schema schema = tx.schema();
//			IdIndex = schema.indexFor(label)
//					.on(property)
//					.withName(labelName)
//					.create();
			idxCreator = schema.indexFor(label);
			for (String p : property) {
				idxCreator = idxCreator.on(p);
			}
			idxDef = idxCreator.withName(label.toString()).create();
			tx.commit();
		}
		try ( Transaction tx = graphDb.beginTx() )
		{
			Schema schema = tx.schema();
			schema.awaitIndexOnline( idxDef, 10, TimeUnit.SECONDS );
		}
		try ( Transaction tx = graphDb.beginTx() )
		{
			Schema schema = tx.schema();
			System.out.printf("%s index: %1.0f%%%n", label.toString(),
					schema.getIndexPopulationProgress( idxDef ).getCompletedPercentage() );
		}
	}

	private void createEdgeIndex(GraphDatabaseService graphDb, RelationshipType relat, List<String> property) {
		IndexDefinition idxDef;
		IndexCreator idxCreator;
		try (Transaction tx = graphDb.beginTx()){
			Schema schema = tx.schema();
//			IdIndex = schema.indexFor(label)
//					.on(property)
//					.withName(labelName)
//					.create();
			idxCreator = schema.indexFor(relat);
			for (String p : property) {
				idxCreator = idxCreator.on(p);
			}
			idxDef = idxCreator.withName(relat.toString()).create();
			tx.commit();
		}
		try ( Transaction tx = graphDb.beginTx() )
		{
			Schema schema = tx.schema();
			schema.awaitIndexOnline( idxDef, 10, TimeUnit.SECONDS );
		}
		try ( Transaction tx = graphDb.beginTx() )
		{
			Schema schema = tx.schema();
			System.out.printf("%s index: %1.0f%%%n", relat.toString(),
					schema.getIndexPopulationProgress( idxDef ).getCompletedPercentage() );
		}
	}
}
