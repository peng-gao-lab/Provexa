package db;

import org.postgresql.util.PSQLException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PostgresUtil {
    private static final String CONFIGS_PATH = "cfg/postgres.properties";
    private static final String tablespaceSql = " TABLESPACE pg_default;";
    private static final String DUP_DB = "42P04";
    private static final String DUP_TABLE = "42P07";
    private static final String createIndexSql = "CREATE INDEX %s_%s ON %s(%s);";
    private static final String[] createTableCmds = {
            "CREATE TABLE public.file " +
            "( " +
            "   id integer NOT NULL, " +
            "   ownergroupid text COLLATE pg_catalog.\"default\", " +
            "   hostname text COLLATE pg_catalog.\"default\" NOT NULL, " +
            "   name text COLLATE pg_catalog.\"default\", " +
            "   owneruserid text COLLATE pg_catalog.\"default\", " +
            "   path text COLLATE pg_catalog.\"default\" NOT NULL, " +
            "   CONSTRAINT file_pkey PRIMARY KEY (id) " +
            ") ",
            "CREATE TABLE public.process " +
            "( " +
            "   id integer NOT NULL, " +
            "   exename text COLLATE pg_catalog.\"default\"," +
            "   exepath text COLLATE pg_catalog.\"default\"," +
            "   ownergroupid text COLLATE pg_catalog.\"default\", " +
            "   hostname text COLLATE pg_catalog.\"default\" NOT NULL, " +
            "   pid integer NOT NULL, " +
            "   owneruserid text COLLATE pg_catalog.\"default\", " +
            "   cmdline text COLLATE pg_catalog.\"default\"," +
            "   CONSTRAINT process_pkey PRIMARY KEY (id) " +
            ") ",
            "CREATE TABLE public.network " +
            "(id integer NOT NULL,"+
            "dstip cidr,"+
            "dstport integer,"+
            "hostname text COLLATE pg_catalog.\"default\" NOT NULL,"+
            "srcip cidr,"+
            "srcport integer,"+
            "CONSTRAINT network_pkey PRIMARY KEY (id))",
            "CREATE TABLE public.fileevent " +
            "( " +
            "   id bigint NOT NULL, " +
            "   amount bigint, " +
            "   dstid integer NOT NULL, " +
            "   endtime numeric(19, 9), " +
            "   eventno bigint NOT NULL, " +
            "   hostname text COLLATE pg_catalog.\"default\" NOT NULL, " +
            "   optype text COLLATE pg_catalog.\"default\", " +
            "   srcid integer NOT NULL, " +
            "   starttime numeric(19, 9), " +
            "   CONSTRAINT fileevent_pkey PRIMARY KEY (id) " +
            ") ",
            "CREATE TABLE public.processevent " +
            "( " +
            "   id bigint NOT NULL, " +
            "   dstid integer NOT NULL, " +
            "   endtime numeric(19, 9), " +
            "   eventno bigint NOT NULL, " +
            "   hostname text COLLATE pg_catalog.\"default\" NOT NULL, " +
            "   optype text COLLATE pg_catalog.\"default\", " +
            "   srcid integer NOT NULL, " +
            "   starttime numeric(19, 9), " +
            "   CONSTRAINT processevent_pkey PRIMARY KEY (id) " +
            ") ",
            "CREATE TABLE public.networkevent " +
            "( " +
            "   id bigint NOT NULL, " +
            "   amount bigint, " +
            "   dstid integer NOT NULL, " +
            "   endtime numeric(19, 9), " +
            "   eventno bigint NOT NULL, " +
            "   hostname text COLLATE pg_catalog.\"default\" NOT NULL, " +
            "   optype text COLLATE pg_catalog.\"default\", " +
            "   srcid integer NOT NULL, " +
            "   starttime numeric(19, 9), " +
            "   CONSTRAINT networkevent_pkey PRIMARY KEY (id) " +
            ") " ,
            "CREATE TABLE public.registry " +
            "( " +
            "   id integer NOT NULL, " +
            "   hostname text COLLATE pg_catalog.\"default\" NOT NULL, " +
            "   registrypath text COLLATE pg_catalog.\"default\" NOT NULL, " +
            "   CONSTRAINT registry_pkey PRIMARY KEY (id) " +
            ") ",
            "CREATE TABLE public.registryevent " +
            "( " +
            "   id bigint NOT NULL, " +
            "   amount bigint, " +
            "   dstid integer NOT NULL, " +
            "   endtime numeric(19, 9), " +
            "   eventno bigint NOT NULL, " +
            "   hostname text COLLATE pg_catalog.\"default\" NOT NULL, " +
            "   optype text COLLATE pg_catalog.\"default\", " +
            "   srcid integer NOT NULL, " +
            "   starttime numeric(19, 9), " +
            "   CONSTRAINT registryevent_pkey PRIMARY KEY (id) " +
            ")"
    };

    private static final Map<String, String[]> indices = Stream.of(
            new AbstractMap.SimpleEntry<>("file", new String[] {"id", "name", "path"}),
            new AbstractMap.SimpleEntry<>("process", new String[] {"id", "exename", "exepath"}),
            new AbstractMap.SimpleEntry<>("network", new String[] {"id", "srcip", "dstip"}),
            new AbstractMap.SimpleEntry<>("registry", new String[] {"id", "registrypath"}),
            new AbstractMap.SimpleEntry<>("processevent", new String[] {"id", "srcid", "dstid"}),
            new AbstractMap.SimpleEntry<>("networkevent", new String[] {"id", "srcid", "dstid"}),
            new AbstractMap.SimpleEntry<>("fileevent", new String[] {"id", "srcid", "dstid"}),
            new AbstractMap.SimpleEntry<>("registryevent", new String[] {"id", "srcid", "dstid"}))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


    private static final String tableCheckFormat = "SELECT EXISTS ( " +
            "   SELECT FROM information_schema.tables  " +
            "   WHERE  table_schema = 'public' " +
            "   AND    table_name   = '%s' " +
            "   );";

    public static Connection getConnection(String dbNameOverride) throws SQLException, IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(CONFIGS_PATH));
        String pgUrl = properties.getProperty("url");
        String dbName = dbNameOverride != null ? dbNameOverride : properties.getProperty("dbName");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        Connection con =  DriverManager.getConnection(pgUrl, username, password);
        try {
            Statement s = con.createStatement();
            s.executeUpdate("CREATE DATABASE " + dbName);
            s.close();
            con.close();
            System.out.println(dbName + " is created");
        } catch (PSQLException se) {
            if(se.getSQLState().equals(DUP_DB)) {
                System.out.println(dbName + " already exists in Postgres");
            }
        }
        return DriverManager.getConnection(pgUrl+dbName, username, password);
    }

    public static Connection getConnection() throws SQLException, IOException {
        return getConnection(null);
    }

    public static void createTable(Connection conn, boolean dropFirst) throws SQLException, IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(CONFIGS_PATH));
        String username = properties.getProperty("username");
        for (String sql : createTableCmds) {
            try {
                Statement stmt = conn.createStatement();
                String tableName = sql.split(" ")[2];
                DatabaseMetaData meta = conn.getMetaData();
                String shortTableName = tableName.split("\\.")[1];
                ResultSet res = meta.getTables(null, null, shortTableName,null);
                if(res.next() && dropFirst) stmt.executeUpdate("DROP TABLE " + tableName);
                stmt.executeUpdate(sql + tablespaceSql);
                stmt.executeUpdate("ALTER TABLE " + tableName + " OWNER to " + username);
                String[] index_columns = indices.get(shortTableName);
                for(String column : index_columns) {
                    String cmd = String.format(createIndexSql, shortTableName, column,  tableName, column);
                    stmt.executeUpdate(cmd);
                }
                stmt.close();
                System.out.format("Table %s is created \n", tableName);
            } catch (PSQLException se) {
                if(se.getSQLState().equals(DUP_TABLE)) {
                    System.out.println("Table exists.");
                }
            }
        }
    }

    public static void createIndices(Connection conn, boolean dropFirst) throws SQLException, IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(CONFIGS_PATH));
        String username = properties.getProperty("username");
        for (String sql : createTableCmds) {
            try {
                Statement stmt = conn.createStatement();
                String tableName = sql.split(" ")[2];
                DatabaseMetaData meta = conn.getMetaData();
                ResultSet res = meta.getTables(null, null, tableName.split("\\.")[1],null);
                if(res.next() && dropFirst) stmt.executeUpdate("DROP TABLE " + tableName);
                stmt.executeUpdate(sql + tablespaceSql);
                stmt.executeUpdate("ALTER TABLE " + tableName + " OWNER to " + username);
                stmt.close();
                System.out.format("Table %s is created \n", tableName);
            } catch (PSQLException se) {
                if(se.getSQLState().equals(DUP_TABLE)) {
                    System.out.println("Table exists.");
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, SQLException {
        getConnection();
    }
}
