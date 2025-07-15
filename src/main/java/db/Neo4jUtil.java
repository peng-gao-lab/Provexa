package db;

import datamodel.*;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;
import org.neo4j.configuration.connectors.BoltConnector;
import org.neo4j.configuration.helpers.SocketAddress;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.configuration.GraphDatabaseSettings;
import org.neo4j.io.fs.FileUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

public class Neo4jUtil {
//    URI u = URI.create("file:///");
    private static final String parentPath = Paths.get(System.getProperty("user.dir")).getParent().toAbsolutePath().toString();
    private static final Path databaseDirectory = Paths.get(parentPath, "neo4j-community-4.3.7");
    private static final int BATCHSIZE = 10000;
    public static String db;

    public static void populateEdges(Connection conn, String type, boolean hasAmount,
                                     GraphDatabaseService graphDb, Neo4j neo4jDb) throws SQLException, IOException {
        String sql = "";
        if (hasAmount) {
            sql = String.format("SELECT id, srcid, dstid, starttime, endtime, hostname, optype, amount FROM %s;", type);
        } else {
            sql = String.format("SELECT id, srcid, dstid, starttime, endtime, hostname, optype FROM %s;", type);
        }
        PreparedStatement sqlStmt = conn.prepareStatement(sql);
        ResultSet results = sqlStmt.executeQuery();
        LinkedList<EventEdge> edges = new LinkedList<>();
        EntityEvent<Entity, Entity> edge = null;
        int count = 0;
        long sTime = System.currentTimeMillis();
        while (results.next()) {
            long id = results.getLong(1);
            long srcId = results.getLong(2);
            long dstId = results.getLong(3);
            BigDecimal startTime = results.getBigDecimal(4);
            BigDecimal endTime = results.getBigDecimal(5);
            String hostName = results.getString(6);
            String opType = results.getString(7);
            // Add edges
            edge = new EntityEvent<Entity, Entity>(type); // This type property is not right, but now useful in Neo4j.java
            edge.setDbID(id);
            edge.setEvent(opType); // The name is confusing (Refer to Postgres.java).
            edge.setStartTime(startTime.toPlainString());
            edge.setEndTime(endTime.toPlainString());
            edge.setSource(new Entity());
            edge.getSource().setUniqID(srcId);
            edge.setSink(new Entity());
            edge.getSink().setUniqID(dstId);
            if (hasAmount) {
                long amount = results.getLong(8);
                edge.setSize(amount);
            }
            EventEdge eedge = new EventEdge(edge);
            eedge.setHost(hostName);
            edges.add(eedge);
            if (++count % BATCHSIZE == 0) {
                switch (type) {
                    case "fileevent":
                        neo4jDb.batchInsertFileEvents(edges, graphDb);
                        break;
                    case "networkevent":
                        neo4jDb.batchInsertNetworkEvents(edges, graphDb);
                        break;
                    case "processevent":
                        neo4jDb.batchInsertProcessEvents(edges, graphDb);
                        break;
                    default:
                        neo4jDb.batchInsertRegistryEvents(edges, graphDb);
                }
                edges = new LinkedList<>();
                if (count % (BATCHSIZE * 100) == 0) {
                    System.out.printf("%s %s edges inserted in %s ms\n", count, type, System.currentTimeMillis()-sTime);
                }
            }
        }
        switch (type) {
            case "fileevent":
                neo4jDb.batchInsertFileEvents(edges, graphDb);
                break;
            case "networkevent":
                neo4jDb.batchInsertNetworkEvents(edges, graphDb);
                break;
            case "processevent":
                neo4jDb.batchInsertProcessEvents(edges, graphDb);
                break;
            default:
                neo4jDb.batchInsertRegistryEvents(edges, graphDb);
        }
        edges = null;
    }

    private static void populateDb(String dbName) throws SQLException, IOException {
        Map<String, FileEntity> fMap = new HashMap<>();
        Map<String, NetworkEntity> nMap = new HashMap<>();
        Map<String, ProcessEntity> pMap = new HashMap<>();
        Map<String, RegistryEntity> rMap = new HashMap<>();
        // Connect to postgreSQL
        Connection conn = PostgresUtil.getConnection(dbName.replaceAll("-", "_"));
        // Connect to neo4j database
        Neo4j neo4jDb = new Neo4j();
        DatabaseManagementService managementService = Neo4jUtil.connectDb(dbName);
        GraphDatabaseService graphDb = managementService.database(dbName);
        long sTime = System.currentTimeMillis();
        try {
            System.out.println("===========================================");
            System.out.printf("Populating database %s to neo4j...%n", dbName);
            // Create indexes (align with PostgreSQL)
            neo4jDb.createIndex(graphDb);
            // Retrieve file nodes
            String sql = "SELECT id, ownergroupid, hostname, name, owneruserid, path FROM file;";
            PreparedStatement sqlStmt = conn.prepareStatement(sql);
            ResultSet results = sqlStmt.executeQuery();
            int count = 0, rcount = 0;
            while (results.next())
            {
                long id = results.getLong(1);
                String ownerGroupId = results.getString(2);
                String hostname = results.getString(3);
                String name = results.getString(4);
                String ownerUserId = results.getString(5);
                String path = results.getString(6);
                FileEntity file = new FileEntity(ownerUserId, ownerGroupId, path, id, hostname, name);
                fMap.put(hostname + path + id, file);
                if (count++ % BATCHSIZE == 0) {
                    rcount += neo4jDb.batchInsertFileNodes(fMap, graphDb);
                    fMap = new HashMap<>();
                }
            }
            rcount += neo4jDb.batchInsertFileNodes(fMap, graphDb);
            fMap = null;
            System.out.printf("%s File nodes has been inserted\n", rcount);
            rcount = 0; count = 0;

            // Retrieve network nodes
            sql = "SELECT id, dstip::text, dstport::text, hostname, srcip::text, srcport::text FROM network;";
            sqlStmt = conn.prepareStatement(sql);
            results = sqlStmt.executeQuery();
            while (results.next()) {
                long id = results.getLong(1);
                String dstIp = results.getString(2);
                String dstPort = results.getString(3);
                String hostname = results.getString(4);
                String srcIp = results.getString(5);
                String srcPort = results.getString(6);
                NetworkEntity network = new NetworkEntity(srcIp, dstIp, srcPort, dstPort, id, hostname);
                nMap.put(srcIp+":"+srcPort+"->"+ dstIp+":"+dstPort + id, network);
                if (count++ % BATCHSIZE == 0) {
                    rcount += neo4jDb.batchInsertNetworkNodes(nMap, graphDb);
                    nMap = new HashMap<>();
                }
            }
            rcount += neo4jDb.batchInsertNetworkNodes(nMap, graphDb);
            nMap = null;
            System.out.printf("%s Network nodes has been inserted\n", rcount);
            rcount = 0; count = 0;

            // Retrieve process nodes
            sql = "SELECT id, exename, exepath, ownergroupid, hostname, pid, owneruserid, cmdline FROM process;";
            sqlStmt = conn.prepareStatement(sql);
            results = sqlStmt.executeQuery();
            while (results.next()) {
                long id = results.getLong(1);
                String exeName = results.getString(2);
                String exePath = results.getString(3);
                String hostname = results.getString(5);
                long pid = results.getLong(6);
                String ownerUserId = results.getString(7);
                String cmdLine = results.getString(8);
                ProcessEntity process = new ProcessEntity(hostname, Long.toString(pid), ownerUserId, id, exePath, exeName);
                process.setCmdLine(cmdLine);
                pMap.put(hostname + pid + id, process);
                if (count++ % BATCHSIZE == 0) {
                    rcount += neo4jDb.batchInsertProcessNodes(pMap, graphDb);
                    pMap = new HashMap<>();
                }
            }
            rcount += neo4jDb.batchInsertProcessNodes(pMap, graphDb);
            pMap = null;
            System.out.printf("%s Process nodes has been inserted\n", rcount);
            rcount = 0; count = 0;

            // Retrieve registry nodes
            sql = "SELECT id, hostname, registrypath FROM registry;";
            sqlStmt = conn.prepareStatement(sql);
            results = sqlStmt.executeQuery();
            while (results.next()) {
                long id = results.getLong(1);
                String hostName = results.getString(2);
                String registryPath = results.getString(3);
                RegistryEntity registry = new RegistryEntity(registryPath, id, hostName);
                rMap.put(registryPath, registry);
                if (count++ % BATCHSIZE == 0) {
                    rcount += neo4jDb.batchInsertRegistryNodes(rMap, graphDb);
                    rMap = new HashMap<>();
                }
            }
            rcount += neo4jDb.batchInsertRegistryNodes(rMap, graphDb);
            rMap = null;
            System.out.printf("%s Registry nodes have been inserted\n", rcount);

            // Retrieve events
            populateEdges(conn, "fileevent", true, graphDb, neo4jDb);
            System.out.println("Fileevent finished");
            populateEdges(conn, "networkevent", true, graphDb, neo4jDb);
            System.out.println("Networkevent finished");
            populateEdges(conn, "processevent", false, graphDb, neo4jDb);
            System.out.println("Processevent finished");
            populateEdges(conn, "registryevent", true, graphDb, neo4jDb);
            System.out.println("Registryevent finished");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        long eTime = System.currentTimeMillis();
        System.out.printf("Database populating finished in %s ms\n", eTime-sTime);
        neo4jDb.databaseCount(dbName, graphDb);
        shutDown(managementService);
    }

    public static DatabaseManagementService connectDb(String dbName) throws IOException {
        DatabaseManagementService managementService = new DatabaseManagementServiceBuilder(databaseDirectory)
                .setConfig(GraphDatabaseSettings.default_database, dbName)
                .build();
        registerShutdownHook(managementService);
        return managementService;
    }

    private static void initNeo4jDb(String dbName) throws IOException, SQLException {
        dbName = dbName.replaceAll("_", "-");
        DatabaseManagementService managementService = new DatabaseManagementServiceBuilder(databaseDirectory)
                .setConfig(GraphDatabaseSettings.default_database, "neo4j")
                .build();
        if (!managementService.listDatabases().contains(dbName)) {
            System.out.printf("Trying to create database %s%n", dbName);
            shutDown(managementService);
            // This command can not run.
//            managementService.createDatabase(dbName);
            System.out.printf("Database %s created.%n", dbName);
            // Populate database
            populateDb(dbName);
        } else {
            System.out.printf("Database %s exists.%n", dbName);
        }
    }

    public static void initNeo4jDbs() throws IOException, SQLException {
        String [] dbs = {
//                "case2_cmd_injection_victim1",
//                "case2_cmd_injection_victim2",
//                "case2_netcat_backdoor",
//                "case2_penetration",
//                "case2_phishing_email",
//                "case2_shellshock_data_leakage_victim1",
//                "case2_shellshock_data_leakage_victim2",
//                "case2_shellshock_password_crack_victim1",
//                "case2_shellshock_password_crack_victim2",
//                "case2_supply_chain",
//                "case2_wannacry",
//                "case3_autorun",
//                "case3_danger",
//                "case3_hijack",
//                "case3_infector",
//                "case3_sysbot",
//                "case4_attack1",
//                "case4_attack10",
//                "case4_attack2",
//                "case4_attack3",
//                "case4_attack4",
//                "case4_attack5",
//                "case4_attack6",
//                "case4_attack7",
//                "case4_attack8",
//                "case4_attack9",
//                "fivedirections",
                "fivedirections2",
                "marple",
                "theia",
                "trace"};
        for (String s : dbs) {
            initNeo4jDb(s);
        }
    }

    public static void cleanDirectory() throws IOException {
		FileUtils.deleteDirectory(databaseDirectory);
		System.out.println("Directory cleaned.");
    }

    public static void runDb(String dbName) throws IOException {
        dbName = dbName.replaceAll("_", "-");
        DatabaseManagementService managementService = new DatabaseManagementServiceBuilder(databaseDirectory)
                .setConfig(BoltConnector.enabled, true)
                .setConfig(BoltConnector.listen_address, new SocketAddress("localhost", 7687))
                .setConfig(GraphDatabaseSettings.default_database, dbName)
                .build();
//		GraphDatabaseService graphDb = managementService.database(dbName);
        registerShutdownHook(managementService);
        System.out.printf( "Running %s on localhost:7687 ...\n", dbName);
    }

    public static void shutDown(DatabaseManagementService managementService)
    {
        System.out.println( "Shutting down database ..." );
        // tag::shutdownServer[]
        managementService.shutdown();
        // end::shutdownServer[]
    }

    // tag::shutdownHook[]
    private static void registerShutdownHook( final DatabaseManagementService managementService )
    {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook(new Thread(managementService::shutdown));
    }

    public static void main(String[] args) throws IOException, SQLException {
//        initNeo4jDbs();
        runDb("dropbox");
    }
}
