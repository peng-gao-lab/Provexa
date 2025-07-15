package executor;

public class ConnectDbStatement implements QueryStatement {
    private final ExecutionContext ctx;
    private final String dbName;

    public ConnectDbStatement(ExecutionContext exeCtx, String dbName) {
        ctx = exeCtx;
        this.dbName = dbName;
    }

    @Override
    public void execute() throws Exception {
        ctx.setConnection(dbName);
    }

    @Override
    public String toString() {
        return "connectdb '" + dbName + "';";
    }
}
