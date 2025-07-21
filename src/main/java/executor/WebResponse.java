package executor;

import java.util.List;

public class WebResponse {
    private final String logs;
    private final List<String> graphs;

    public WebResponse(String logs, List<String> graphs) {
        this.logs = logs;
        this.graphs = graphs;
    }

    public String getLogs() {
        return logs;
    }

    public List<String> getGraphs() {
        return graphs;
    }
}
