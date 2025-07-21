package parser.sysdigparser;

import datamodel.*;
import datamodel.ProcessEntity;
import java.io.IOException;
import java.util.Map;

public interface SysdigOutputParser {
    Map<String, PtoFEvent> getPtoFMap();

    Map<String, PtoNEvent> getPtoNMap();

    Map<String, PtoPEvent> getPtoPMap();

    Map<String, NtoPEvent> getNtoPMap();

    Map<String, FtoPEvent> getFtoPMap();
    
    Map<String, FileEntity> getFileMap();
    
    Map<String, ProcessEntity> getProcessMap();

    Map<String, NetworkEntity> getNetworkMap();
    
    void getEntities() throws IOException;

    Map<String, RegistryEntity> getRegistryMap();

    Map<String, PtoREvent> getPtoRMap();

    Map<String, RtoPEvent> getRtoPMap();
}
