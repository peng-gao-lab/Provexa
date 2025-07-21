package db;

import datamodel.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Postgres {	

	private Map<String, FileEntity> fileMap;
    private Map<String, NetworkEntity> networkMap;
    private Map<String, ProcessEntity> processMap;
	private Map<String, RegistryEntity> registryMap;
    private List<EventEdge> processFileLst;
    private List<EventEdge> processNetworkLst;
    private List<EventEdge> processProcessLst;
    private List<EventEdge> processRegistryLst;

    private final int BATCH_SIZE = 100;
    
	public Postgres(Map<String, FileEntity> fileMap,Map<String, NetworkEntity> networkMap,Map<String, ProcessEntity> processMap,
			List<EventEdge> processFileLst, List<EventEdge> processNetworkLst, List<EventEdge> processProcessLst) {
		this.fileMap = fileMap;
		this.networkMap = networkMap;
		this.processMap = processMap;
		this.processFileLst = processFileLst;
		this.processNetworkLst = processNetworkLst;
		this.processProcessLst = processProcessLst;
		this.registryMap = null;
		this.processRegistryLst = null;
	}

	public Postgres(Map<String, FileEntity> fileMap,
					Map<String, NetworkEntity> networkMap,
					Map<String, ProcessEntity> processMap,
					Map<String, RegistryEntity> registryMap,
					List<EventEdge> processFileLst,
					List<EventEdge> processNetworkLst,
					List<EventEdge> processProcessLst,
					List<EventEdge> processRegistryLst) {
		this.fileMap = fileMap;
		this.networkMap = networkMap;
		this.processMap = processMap;
		this.processFileLst = processFileLst;
		this.processNetworkLst = processNetworkLst;
		this.processProcessLst = processProcessLst;
		this.registryMap = registryMap;
		this.processRegistryLst = processRegistryLst;
	}

	public void createDb() {
		this.createDb(null);
	}

	public void createDb(String dbName) {
		Connection conn = null;
		try {
			if (dbName == null) {
				conn = PostgresUtil.getConnection();
			} else {
				conn = PostgresUtil.getConnection(dbName);
			}
			PostgresUtil.createTable(conn, true);
			conn.setAutoCommit(false);

			batchInsertFiles(conn);
			batchInsertProcesses(conn);
			batchInsertNetworks(conn);
			if (registryMap != null) {
				batchInsertRegistries(conn);
			}

			batchInsertEventEdges(conn, processFileLst, "FileEvent", true);
			batchInsertEventEdges(conn, processNetworkLst, "NetworkEvent", true);
			batchInsertEventEdges(conn, processProcessLst, "ProcessEvent", false);
			if (processRegistryLst != null) {
				batchInsertEventEdges(conn, processRegistryLst, "RegistryEvent", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					System.out.println("Transaction is being rolled back.");
					conn.rollback();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	private void batchInsertFiles(Connection conn) throws SQLException {
		List<FileEntity> fLst = new ArrayList<>(fileMap.values());
		String sql = "INSERT INTO file (id,ownergroupid,hostname,name,owneruserid, path) VALUES (?,?,?,?,?,?);";
		PreparedStatement stmt = conn.prepareStatement(sql);
		Date startDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String start = sdf.format(startDate);
		System.out.println("Batch insert file start::::::::::::::::"+start);

		int counter = 1;
		for(int i = 0; i < fLst.size(); i++) {
			FileEntity f = fLst.get(i);
			stmt.setInt(1, Math.toIntExact(f.getUniqID()));
			stmt.setString(2, f.getGroup_ID());
			stmt.setString(3, f.getHost());
			stmt.setString(4, f.getName());
			stmt.setString(5, f.getUser_ID());
			stmt.setString(6, f.getPath());
			stmt.addBatch();

			//Execute batch of 100 records
			if(i % BATCH_SIZE == 0){
				stmt.executeBatch();
				conn.commit();
				System.out.println("Batch "+(counter++)+" executed successfully");
			}
		}
		//execute final batch
		stmt.executeBatch();
		conn.commit();
		System.out.println("Final batch executed successfully");

		Date endDate = new Date();
		String end = sdf.format(endDate);
		System.out.println("Batch insert file end::::::::::::::::"+end);
	}
	
	private void batchInsertProcesses(Connection conn) throws SQLException {
		String sql = "INSERT INTO Process (id,exename,exepath,ownergroupid,hostname,pid,owneruserid,cmdline) VALUES (?,?,?,?,?,?,?,?);";
		List<ProcessEntity> pLst = new ArrayList<>(processMap.values());
		PreparedStatement stmt = conn.prepareStatement(sql);
		Date startDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String start = sdf.format(startDate);
		System.out.println("Batch insert process start::::::::::::::::"+start);

		int counter = 1;
		for(int i = 0; i < pLst.size(); i++) {
			ProcessEntity p = pLst.get(i);
			stmt.setInt(1, Math.toIntExact(p.getUniqID()));
			stmt.setString(2, p.getName());
			stmt.setString(3, p.getExePath());
			stmt.setString(4, p.getGroupID());
			stmt.setString(5, p.getHost());
			stmt.setInt(6, Integer.parseInt(p.getPid()));
			stmt.setString(7, p.getUid());
			stmt.setString(8, p.getCmdLine());

			stmt.addBatch();

			//Execute batch of 100 records
			if(i % BATCH_SIZE == 0){
				stmt.executeBatch();
				conn.commit();
				System.out.println("Batch "+(counter++)+" executed successfully");
			}
		}
		//execute final batch
		stmt.executeBatch();
		conn.commit();
		System.out.println("Final batch executed successfully");

		Date endDate = new Date();
		String end = sdf.format(endDate);
		System.out.println("Batch insert process end::::::::::::::::"+end);
	}
	
	private void batchInsertNetworks(Connection conn) throws SQLException {
		String sql = "INSERT INTO Network (id,dstip,dstport,hostname,srcip,srcport) VALUES (?,?::cidr,?,?,?::cidr,?);";
		List<NetworkEntity> nLst = new ArrayList<>(networkMap.values());
		PreparedStatement stmt = conn.prepareStatement(sql);
		Date startDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String start = sdf.format(startDate);
		System.out.println("Batch insert network start::::::::::::::::"+start);

		int counter = 1;
		for(int i = 0; i < nLst.size(); i++) {
			NetworkEntity n = nLst.get(i);
			stmt.setInt(1, Math.toIntExact(n.getUniqID()));
			stmt.setString(2, n.getDstAddress().isEmpty()?null:n.getDstAddress());
			stmt.setInt(3, Integer.parseInt(n.getDstPort()));
			stmt.setString(4, n.getHost());
			stmt.setString(5, n.getSrcAddress().isEmpty()?null:n.getSrcAddress());
			stmt.setInt(6, Integer.parseInt(n.getSrcPort()));

			stmt.addBatch();

			//Execute batch of 100 records
			if(i % BATCH_SIZE == 0){
				stmt.executeBatch();
				conn.commit();
				System.out.println("Batch "+(counter++)+" executed successfully");
			}
		}
		//execute final batch
		stmt.executeBatch();
		conn.commit();
		System.out.println("Final batch executed successfully");

		Date endDate = new Date();
		String end = sdf.format(endDate);
		System.out.println("Batch insert network end::::::::::::::::"+end);
	}

	private void batchInsertRegistries(Connection conn) throws SQLException {
		List<RegistryEntity> rLst = new ArrayList<>(registryMap.values());
		String sql = "INSERT INTO registry (id, hostname, registrypath) VALUES (?,?,?);";
		PreparedStatement stmt = conn.prepareStatement(sql);
		Date startDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String start = sdf.format(startDate);
		System.out.println("Batch insert registry start::::::::::::::::"+start);

		int counter = 1;
		for(int i = 0; i < rLst.size(); i++) {
			RegistryEntity r = rLst.get(i);
			stmt.setInt(1, Math.toIntExact(r.getUniqID()));
			stmt.setString(2, r.getHost());
			stmt.setString(3, r.getPath());
			stmt.addBatch();

			//Execute batch of 100 records
			if(i % BATCH_SIZE == 0){
				stmt.executeBatch();
				conn.commit();
				System.out.println("Batch "+(counter++)+" executed successfully");
			}
		}
		//execute final batch
		stmt.executeBatch();
		conn.commit();
		System.out.println("Final batch executed successfully");

		Date endDate = new Date();
		String end = sdf.format(endDate);
		System.out.println("Batch insert registry end::::::::::::::::"+end);
	}

	private void batchInsertEventEdges(Connection conn, List<EventEdge> edges,
									   String table, boolean hasAmount) throws SQLException {
		String sql = "INSERT INTO " + table;
		if (hasAmount) {
			sql += " (id,starttime,endtime,srcid,dstid,optype,hostname,eventno,amount) VALUES (?,?,?,?,?,?,?,?,?);";
		} else {
			sql += " (id,starttime,endtime,srcid,dstid,optype,hostname,eventno) VALUES (?,?,?,?,?,?,?,?);";
		}

		PreparedStatement stmt = conn.prepareStatement(sql);
		Date startDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String start = sdf.format(startDate);
		System.out.println("Batch insert " + table + " start::::::::::::::::" + start);
		System.out.println("Size of edges:" + edges.size());

		int counter = 1;
		for(int i = 0; i < edges.size(); i++) {
			EventEdge e = edges.get(i);
			stmt.setLong(1, e.getID());
			stmt.setBigDecimal(2, e.getStartTime());
			stmt.setBigDecimal(3, e.getEndTime());
			stmt.setInt(4, Math.toIntExact(e.getSource().getID()));
			stmt.setInt(5, Math.toIntExact(e.getSink().getID()));
			stmt.setString(6, e.getEvent()); // event->optype, the name is a little confusing.
			stmt.setString(7, e.getHost());
			stmt.setLong(8, e.getEventNo());
			if (hasAmount) stmt.setLong(9, e.getSize());

			stmt.addBatch();

			//Execute batch of 100 records
			if(i % BATCH_SIZE == 0){
				stmt.executeBatch();
				conn.commit();
				System.out.println("Batch "+(counter++)+" executed successfully");
			}
		}
		//execute final batch
		stmt.executeBatch();
		conn.commit();
		System.out.println("Final batch executed successfully");

		Date endDate = new Date();
		String end = sdf.format(endDate);
		System.out.println("Batch insert " + table + " end::::::::::::::::" + end);
	}
}
