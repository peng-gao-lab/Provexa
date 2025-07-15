package parser.sysdigparser;

import datamodel.*;
import datamodel.ProcessEntity;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class ProcessOriginalParserOutput {
    private Map<String, PtoFEvent> processFileMap;
    private Map<String, PtoPEvent> processProcessMap;
    private Map<String, FtoPEvent> fileProcessMap;
    private Map<String, PtoNEvent> pnmap;              // this one is just according to ip direction(local->remote)
    private Map<String, NtoPEvent> npmap;              // this one is just according to ip direction(remote -> local)
    private SysdigOutputParser parser;
    
    private Map<String, FileEntity> fileMap;
    private Map<String, ProcessEntity> processMap;
    private Map<String, NetworkEntity> networkMap;

    public ProcessOriginalParserOutput(String logFilePath){
        parser = new SysdigOutputParserNoRegex(logFilePath);
        try {
            parser.getEntities();
        } catch (IOException e){
            System.out.println("The log file doesn't exist");
        }
        processFileMap = parser.getPtoFMap();
        pnmap = parser.getPtoNMap();
        processProcessMap = parser.getPtoPMap();
        npmap = parser.getNtoPMap();
        fileProcessMap = parser.getFtoPMap();
        
        
        fileMap = parser.getFileMap();
        processMap = parser.getProcessMap();
        networkMap = parser.getNetworkMap();
    }
    public Map<String, PtoFEvent> getProcessFileMap() {
        return processFileMap;
    }

    public Map<String, PtoNEvent> getProcessNetworkMap() {
        return pnmap;
    }

    public Map<String, PtoPEvent> getProcessProcessMap() {
        return processProcessMap;
    }

    public Map<String, NtoPEvent> getNetworkProcessMap() {
        return npmap;
    }

    public Map<String, FtoPEvent> getFileProcessMap() {
        return fileProcessMap;
    }

    public Map<String, FileEntity> getFileMap() {
		return fileMap;
	}

	public Map<String, ProcessEntity> getProcessMap() {
		return processMap;
	}

	public Map<String, NetworkEntity> getNetworkMap() {
		return networkMap;
	}

	public SysdigOutputParser getParser() { return parser; }

}
