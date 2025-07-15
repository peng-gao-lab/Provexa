package query;
import datamodel.*;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Utils {
    public static EntityNode createNode(Node n) {
        EntityNode res;
        switch ((String) n.getProperty("type")) {
            case "file": {
                FileEntity e = new FileEntity((String) n.getProperty("owneruserid"),
                        (String) n.getProperty("ownergroupid"),
                        (String) n.getProperty("path"),
                        (Long) n.getProperty("id"),
                        (String) n.getProperty("hostname"),
                        (String) n.getProperty("name"));
                res = new EntityNode(e);
                break;
            }
            case "process": {
                ProcessEntity e = new ProcessEntity((String) n.getProperty("hostname"),
                        String.valueOf(n.getProperty("pid")),
                        (String) n.getProperty("owneruserid"),
                        (Long) n.getProperty("id"),
                        (String) n.getProperty("exepath"),
                        (String) n.getProperty("exename"));
                res = new EntityNode(e);
                break;
            }
            case "network": {
                NetworkEntity e = new NetworkEntity((String) n.getProperty("srcip"),
                        (String) n.getProperty("dstip"),
                        String.valueOf(n.getProperty("srcport")),
                        String.valueOf(n.getProperty("dstport")),
                        (Long) n.getProperty("id"),
                        (String) n.getProperty("hostname"));
                res = new EntityNode(e);
                break;
            }
            default: {
                RegistryEntity e = new RegistryEntity( (String) n.getProperty("path"),
                        (Long) n.getProperty("id"),
                        (String) n.getProperty("host"));
                res = new EntityNode(e);
            }
        }
        return res;
    }

    public static EventEdge createEdge(EntityNode source, EntityNode sink, Relationship r) {
        return new EventEdge(source, sink,
                (Long) r.getProperty("id"),
                (String) r.getProperty("optype"),
                (Long) r.getProperty("amount"),
                new BigDecimal((Long) r.getProperty("starttime")),
                new BigDecimal((Long) r.getProperty("endtime")),
                (String) r.getProperty("hostname"));
    }

    // Properties -> Entity types
    public static final HashMap<String, String> P2T = new HashMap<>(Map.of(
            "name", "file",
            "path", "file",
            "dstip", "network",
            "srcid", "network",
            "dstport", "network",
            "srcport", "network",
            "pid", "process",
            "exename", "process",
            "exepath", "process",
            "cmdline", "process"
    ));

    // Get event type
    public static String getEventType(String source, String sink) {
        ArrayList<String> types = new ArrayList<>();
        types.add(source);
        types.add(sink);
        if(types.contains("network")) return "networkevent";
        else if(types.contains("file")) return "fileevent";
        else return "processevent";
    }

    public static void extractFileNodes(Connection conn, HashMap<Integer, EntityNode> nodeMap) throws SQLException {
        String sql = "SELECT * FROM file WHERE id = any(?);";
        PreparedStatement sqlStmt = conn.prepareStatement(sql);
        Array ids = conn.createArrayOf("integer", nodeMap.keySet().toArray());
        sqlStmt.setArray(1, ids);
        ResultSet results = sqlStmt.executeQuery();
        while (results.next()) {
            int id = results.getInt(1);
            String ownerGroupId = results.getString(2);
            String hostname = results.getString(3);
            String name = results.getString(4);
            String ownerUserId = results.getString(5);
            String path = results.getString(6);

            FileEntity file = new FileEntity(ownerUserId, ownerGroupId, path, id, hostname, name);
            nodeMap.get(id).setF(file);
        }
    }

    public static void extractNetworkNodes(Connection conn, HashMap<Integer, EntityNode> nodeMap) throws SQLException {
        String sql = "SELECT id, dstip::text, dstport::text, hostname, srcip::text, srcport::text FROM network WHERE id = any(?);";
        PreparedStatement sqlStmt = conn.prepareStatement(sql);
        Array ids = conn.createArrayOf("integer", nodeMap.keySet().toArray());
        sqlStmt.setArray(1, ids);
        ResultSet results = sqlStmt.executeQuery();
        while (results.next()) {
            int id = results.getInt(1);
            String dstIp = results.getString(2);
            String dstPort = results.getString(3);
            String hostname = results.getString(4);
            String srcIp = results.getString(5);
            String srcPort = results.getString(6);

            NetworkEntity network = new NetworkEntity(srcIp, dstIp, srcPort, dstPort, id, hostname);
            nodeMap.get(id).setN(network);
        }
    }

    public static void extractProcessNodes(Connection conn, HashMap<Integer, EntityNode> nodeMap) throws SQLException {
        String sql = "SELECT * FROM process WHERE id = any(?);";
        PreparedStatement sqlStmt = conn.prepareStatement(sql);
        Array ids = conn.createArrayOf("int", nodeMap.keySet().toArray());
        sqlStmt.setArray(1, ids);
        ResultSet results = sqlStmt.executeQuery();
        while (results.next()) {
            int id = results.getInt(1);
            String exeName = results.getString(2);
            String exePath = results.getString(3);
            String hostname = results.getString(5);
            int pid = results.getInt(6);
            String ownerUserId = results.getString(7);
            String cmdLine = results.getString(8);

            ProcessEntity process = new ProcessEntity(hostname, Integer.toString(pid), ownerUserId, id, exePath, exeName);
            process.setCmdLine(cmdLine);
            nodeMap.get(id).setP(process);
        }
    }

    public static double getInterval(List<String> opt) {
        double time;
        if (opt.get(2).equals("ms")) {
            time = Integer.parseInt(opt.get(1)) / 1000.0;
        } else if (opt.get(2).equals("m")) {
            time = Integer.parseInt(opt.get(1)) * 1000.0;
        } else {
            time = Integer.parseInt(opt.get(1));
        }
        return time;
    }


}
