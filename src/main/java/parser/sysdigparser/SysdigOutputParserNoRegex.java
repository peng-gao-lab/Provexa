package parser.sysdigparser;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import config.MetaConfig;
import datamodel.Entity;
import datamodel.EntityEvent;
import datamodel.FileEntity;
import datamodel.FtoPEvent;
import datamodel.NetworkEntity;
import datamodel.NtoPEvent;
import datamodel.ProcessEntity;
import datamodel.PtoFEvent;
import datamodel.PtoNEvent;
import datamodel.PtoPEvent;
import datamodel.PtoREvent;
import datamodel.RegistryEntity;
import datamodel.RtoPEvent;
import parser.ParserUtils;
import parser.exception.EventStartUnseenException;
import parser.exception.UnknownEventException;
import parser.sysdigparser.syscalls.Fingerprint;
import parser.sysdigparser.syscalls.SystemCall;
import parser.sysdigparser.syscalls.SystemCallFactory;

public class SysdigOutputParserNoRegex implements SysdigOutputParser {

    private File log;
    private long UID;

    private HashMap<String, FileEntity> files;
    private HashMap<String, ProcessEntity> processes;
    private HashMap<String, NetworkEntity> networks;

    private HashMap<String, PtoFEvent> pfEvent; //keys are all timestamps:event:cwd
    private HashMap<String, PtoNEvent> pnEvent;
    private HashMap<String, PtoPEvent> ppEvent;
    private HashMap<String, NtoPEvent> npEvent;
    private HashMap<String, FtoPEvent> fpEvent;

    private Map<Fingerprint, SystemCall> answering;

    private Map<String,Map<String, String>> incompleteEvents; //key is timestamp:event:cwd
    private Map<String,PtoPEvent> backFlow; //key is pid+process
    private Map<String,PtoPEvent> forwardFlow; //key is pid+process

    private static final Pattern pParent = Pattern.compile("ptid=(?<parentPID>\\d+)\\((?<parent>.+?)\\)");

    public SysdigOutputParserNoRegex(String pathToLog) {
        files = new HashMap<>();
        processes = new HashMap<>();
        networks = new HashMap<>();

        pfEvent = new HashMap<>();
        pnEvent = new HashMap<>();
        ppEvent = new HashMap<>();
        npEvent = new HashMap<>();
        fpEvent = new HashMap<>();

        incompleteEvents = new HashMap<>();
        backFlow = new HashMap<>();
        forwardFlow = new HashMap<>();

        answering = new HashMap<>();

        log = new File(pathToLog);
        UID = 0;

        registerSystemCalls();
    }

    private void registerSystemCalls(){
        //FtoP
        SystemCallFactory f2pSystemCall = new SystemCallFactory("FtoP",FileEntity.class,null)
                .addAction(this::updateP2PLinks)
                .addAction(this::addF2PEvent);
        for(String s : MetaConfig.ftopSystemCall) {
            SystemCall systemCall = f2pSystemCall.getSystemCall(s);
            answering.put(systemCall.fingerPrint,systemCall);
        }

        //PtoF
        SystemCallFactory p2fSystemCall = new SystemCallFactory("PtoF",FileEntity.class,null)
                .addAction(this::updateP2PLinks)
                .addAction(this::addP2FEvent);
        for(String s : MetaConfig.ptofSystemCall) {
            SystemCall systemCall = p2fSystemCall.getSystemCall(s);
            answering.put(systemCall.fingerPrint,systemCall);
        }

        //NtoP
        SystemCallFactory n2pSystemCall = new SystemCallFactory("NtoP",NetworkEntity.class,null)
                .addAction(this::updateP2PLinks)
                .addAction(this::addN2PEvent);
        for(String s : MetaConfig.ntopSystemCall) {
            SystemCall systemCall = n2pSystemCall.getSystemCall(s);
            answering.put(systemCall.fingerPrint,systemCall);
        }


        //PtoN
        SystemCallFactory p2nSystemCall = new SystemCallFactory("PtoN",NetworkEntity.class,null)
                .addAction(this::updateP2PLinks)
                .addAction(this::addP2NEvent);
        for(String s : MetaConfig.ptonSystemCall) {
            SystemCall systemCall = p2nSystemCall.getSystemCall(s);
            answering.put(systemCall.fingerPrint,systemCall);
        }

        //execve
        SystemCall execve = new SystemCall("execve","PtoP",
                new Fingerprint("execve",FileEntity.class,null)).addAction((mStart,mEnd,eStart,eEnd)->{
            ProcessEntity pStart = (ProcessEntity)eStart[0];
            ProcessEntity pEnd = (ProcessEntity)eEnd[0];
            FileEntity f = (FileEntity)eStart[1];

            String eventNoStart = mStart.get("eventno");
            String exePath = mStart.get("exepath");
            String timestampStart = mStart.get("timestamp");
            String timestampEnd = mEnd.get("timestamp");
            String cwd = mStart.get("cwd");
            String key =timestampStart + ":execve:" + cwd;

            String[] timestampsStart = timestampStart.split("\\.");
            String[] timestampsEnd = timestampEnd.split("\\.");

            String args = mEnd.get("args");
            String cmdLine = mEnd.get("cmdLine");
            if(!args.contains("res=0"))
                return;

            if(!f.getPath().equals("<NA>")){
                FtoPEvent fp = new FtoPEvent(timestampsStart[0],timestampsStart[1],
                        f,pEnd,"execve",0,Long.parseLong(eventNoStart));
                fp.setEndTime(timestampStart);
                fpEvent.put(key,fp);
            }

            Matcher mParent = pParent.matcher(args);

            if(mParent.find()){
                String pidParent = mParent.group("parentPID");
                String nameParent = mParent.group("parent");
                String keyParent = pidParent+nameParent;
                ProcessEntity parent = processes.computeIfAbsent(keyParent, k ->new ProcessEntity(-1, pidParent,
                        null, null, null,
                        timestampsStart[0], timestampsStart[1],
                        nameParent, UID++, exePath, cmdLine));
//                        if(!processes.containsKey(processKey))
//                            throw new ParentNotSeenException(m.get("id")+" "+m.get("event")
//                                    +":"+pid+" "+process);

                PtoPEvent forwardLink = new PtoPEvent(timestampsStart[0],timestampsStart[1],parent,pEnd,"execve",
                        Long.parseLong(eventNoStart));
                forwardLink.setEndTime(timestampEnd);

                ppEvent.put(key,forwardLink);
                forwardFlow.put(keyParent,forwardLink);

                String eventNoEnd = mEnd.get("eventno");
                PtoPEvent backLink = new PtoPEvent(timestampsStart[0], timestampsStart[1], pEnd,parent, "execve",
                        Long.parseLong(eventNoEnd));
                backLink.setEndTime(timestampEnd);

//                        System.out.println(String.format("%s %s -> %s %s",
//                                backLink.getSource().getPid(),backLink.getSource().getName(),
//                                backLink.getSink().getPid(),backLink.getSink().getName()
//                        ));

                backFlow.put(mEnd.get("pid")+mEnd.get("process"),backLink);
                ppEvent.put(key+"back",backLink);
            }


        });
        answering.put(execve.fingerPrint,execve);

        //accept
        SystemCall accept = new SystemCall("accept","NtoP",new Fingerprint("accept",null,NetworkEntity.class));
        accept.addAction((mStart,mEnd,entitiesStart,entitiesEnd)->{

            String timestamp = mEnd.get("timestamp");
            String cwd = mEnd.get("cwd");
            String key = timestamp+":accept:"+cwd;

            NtoPEvent np = extractEventInfo(new NtoPEvent(), mStart, mEnd, entitiesEnd[1], entitiesEnd[0]);
            PtoNEvent pn = extractEventInfo(new PtoNEvent(), mEnd, mEnd, entitiesEnd[0], entitiesEnd[1]);
            np.setStartTime(timestamp);
            npEvent.put(key, np);
            pnEvent.put(key, pn);
        });
        answering.put(accept.fingerPrint,accept);

        //fcntl
        SystemCall fcntl = new SystemCall("fcntl","NtoP",new Fingerprint("fcntl",null,NetworkEntity.class));
        fcntl.addAction((mStart,mEnd,entitiesStart,entitiesEnd)->{
            NtoPEvent e = extractEventInfo(new NtoPEvent(), mStart, mEnd, entitiesEnd[1], entitiesEnd[0]);
            npEvent.put(extractEventKey(mStart), e);
        });
        answering.put(fcntl.fingerPrint,fcntl);

        //rename
        SystemCall rename = new SystemCall("rename", "PtoF", new Fingerprint("rename", null, null));
        rename.addAction((mStart, mEnd, entitiesStart, entitiesEnd)->{
        	
            String timestampStart = mStart.get("timestamp");
            String timestampEnd = mEnd.get("timestamp");

            String[] timestampsStart = timestampStart.split("\\.");

            String event = mEnd.get("event");
            String args = mEnd.get("args");
            String cwd = mEnd.get("cwd");

            String oldPath = args.substring(args.indexOf("oldpath=")+8, args.lastIndexOf(" newpath"));
            String newPath = args.substring(args.indexOf("newpath=")+8, args.lastIndexOf(" "));

            if(oldPath.endsWith(")")) oldPath = oldPath.substring(oldPath.indexOf("(")+1, oldPath.length()-1);
            if(newPath.endsWith(")")) newPath = newPath.substring(newPath.indexOf("(")+1, newPath.length()-1);

            final String realOldPath = oldPath;
            final String realNewPath = newPath;

            String[] realOldNames = realOldPath.split("/");
            String[] realNewNames = realNewPath.split("/");

            ProcessEntity p = (ProcessEntity)entitiesStart[0];
            String key = timestampStart+":"+event+":"+cwd;

            String eventNoStart = mStart.get("eventno");
            FileEntity oldFile = files.computeIfAbsent(oldPath ,k -> new FileEntity( 0L, timestampsStart[0],
                    timestampsStart[1], null, null, realOldPath, UID++, realOldNames[realOldNames.length - 1]));
            FtoPEvent fp = new FtoPEvent(timestampsStart[0],timestampsStart[1],
                    oldFile, p, mStart.get("event"),0,Long.parseLong(eventNoStart));
            fp.setEndTime(timestampEnd);
            fpEvent.put(key,fp);

            String eventNoEnd = mEnd.get("eventno");
            FileEntity newFile = files.computeIfAbsent(newPath ,k -> new FileEntity( 0L, timestampsStart[0],
                    timestampsStart[1], null, null, realNewPath, UID++, realNewNames[realNewNames.length - 1]));
            PtoFEvent pf = new PtoFEvent(timestampsStart[0],timestampsStart[1],
                    p, newFile, mStart.get("event"),0,Long.parseLong(eventNoEnd));
            pf.setEndTime(timestampEnd);
            pfEvent.put(key,pf);
        });
        answering.put(rename.fingerPrint, rename);
    }

    public void getEntities() throws IOException{
        System.out.println("Parsing...");
        long start = System.currentTimeMillis();
        //dependencyGraph = new DirectedPseudograph<pagerank.EntityNode, pagerank.EventEdge>(pagerank.EventEdge.class);
        BufferedReader logReader = new BufferedReader(new FileReader(log),1048576);
        String currentLine;
        while((currentLine = logReader.readLine())!=null){
            Map matcher = ParserUtils.parseEntry(currentLine);
            if (matcher == null || matcher.isEmpty()) {
                continue;
            }
            if(!matcher.isEmpty()){
                if(matcher.get("direction").equals(">")){
                    incompleteEvents.put(matcher.get("timestamp")+":"+matcher.get("event")+":"+matcher.get("cwd"),matcher);
                }else{
                    try{
                        processEvent(matcher);
                    }catch (Exception e){
//                        e.printStackTrace();
                        //System.out.println(e.getMessage());
                    }
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Parsing(in parser) time Cost:"+(end-start)/1000.0);
    }



    private void processEvent(Map<String, String> end) throws EventStartUnseenException, UnknownEventException {
        String startTimestamp = new BigDecimal(end.get("timestamp"))
                .subtract(new BigDecimal(end.get("latency")).scaleByPowerOfTen(-9))
                .toString();
        String key = startTimestamp+":"+end.get("event")+":"+end.get("cwd");
//        System.out.println("**********key:"+startTimestamp+":"+end.get("event")+":"+end.get("cwd"));
        Map start;
        if (!incompleteEvents.containsKey(key)){
            String dummyEntry = String.format("%s %s %s %s (%s) %s %s cwd=%s !dummy!  latency=%s",
                    "0",startTimestamp,end.get("cpu"),
                    end.get("process"),end.get("pid"),">",end.get("event"),
                    end.get("cwd"),end.get("latency"));
            start = ParserUtils.parseEntry(dummyEntry);

        } else {
            start = incompleteEvents.remove(key);
        }

        Entity[] startEntites = extractEntities(start);
        Entity[] endEntities = extractEntities(end);

        Fingerprint f = Fingerprint.toFingerPrint(start, end, startEntites, endEntities);
        SystemCall systemCall = answering.getOrDefault(f, null);
        if (systemCall == null) {
            throw new UnknownEventException("Unknown event: "+start.get("event"));
        }else{
            systemCall.react(start, end, startEntites, endEntities);
        }
        if(start.get("args").equals("!dummy!"))
            System.out.println("Event enter point not seen: "+end.get("raw"));
    }

    public void updateP2PLinks(Map<String, String> mStart, Map<String, String> mEnd, Entity[] entitiesStart, Entity[] entitiesEnd){
        String endTime = mEnd.get("timestamp");
        if(backFlow.containsKey(mEnd.get("pid")+mEnd.get("process"))){
            PtoPEvent bf = backFlow.get(mEnd.get("pid")+mEnd.get("process"));
            if(new BigDecimal(bf.getEnd()).compareTo(new BigDecimal(endTime))<0){
                bf.setEndTime(endTime);
            }
//            System.out.println(bf.getEnd());
        }
        if(forwardFlow.containsKey(mEnd.get("pid")+mEnd.get("process"))){
            PtoPEvent ff = forwardFlow.get(mEnd.get("pid")+mEnd.get("process"));
            if(new BigDecimal(ff.getEnd()).compareTo(new BigDecimal(endTime))<0){
                ff.setEndTime(endTime);
            }
//            System.out.println(ff.getEnd());
        }
    }

    private <T extends EntityEvent> T extractEventInfo(T e, Map<String, String> mStart, Map<String, String> mEnd,
                                  Entity source, Entity sink) {
        long eventNo = Long.parseLong(mStart.get("eventno"));
        String timestampStart = mStart.get("timestamp");
        String timestampEnd = mEnd.get("timestamp");
        String event = mStart.get("event");

        String args = mEnd.get("args");
        long size = ParserUtils.extractSize(args);

        e.setStartTime(timestampStart);
        e.setEndTime(timestampEnd);
        e.setSource(source);
        e.setSink(sink);
        e.setEvent(event);
        e.setSize(size);
        e.setEventNo(eventNo);
        return e;
    }

    private String extractEventKey(Map<String, String> map) {
        String timestampStart = map.get("timestamp");
        String event = map.get("event");
        String cwd = map.get("cwd");
        return timestampStart+":"+event+":"+cwd;
    }

    private void addP2FEvent(Map<String, String> mStart, Map<String, String> mEnd, Entity[] entitiesStart, Entity[] entitiesEnd){
        PtoFEvent e = extractEventInfo(new PtoFEvent(), mStart, mEnd, entitiesStart[0], entitiesStart[1]);
        if (e.getSize() != -1L) pfEvent.put(extractEventKey(mStart), e);
    }

    private void addF2PEvent(Map<String, String> mStart, Map<String, String> mEnd, Entity[] entitiesStart, Entity[] entitiesEnd){
        FtoPEvent e = extractEventInfo(new FtoPEvent(), mStart, mEnd, entitiesStart[1], entitiesStart[0]);
        if (e.getSize() != -1L) fpEvent.put(extractEventKey(mStart), e);
    }

    private void addP2NEvent(Map<String, String> mStart, Map<String, String> mEnd, Entity[] entitiesStart, Entity[] entitiesEnd){
        PtoNEvent e = extractEventInfo(new PtoNEvent(), mStart, mEnd, entitiesStart[0], entitiesStart[1]);
        if (e.getSize() != -1L) pnEvent.put(extractEventKey(mStart), e);
    }

    private void addN2PEvent(Map<String, String> mStart, Map<String, String> mEnd, Entity[] entitiesStart, Entity[] entitiesEnd){
        NtoPEvent e = extractEventInfo(new NtoPEvent(), mStart, mEnd, entitiesStart[1], entitiesStart[0]);
        if (e.getSize() != -1L) npEvent.put(extractEventKey(mStart), e);
    }

    //todo: support IPv6
    private Entity[] extractEntities(Map<String, String> m){
        Entity[] res = new Entity[2];

        //extract process
        long id = 0L;
        String pid = m.get("pid");
        String process = m.get("process");
        String processKey = pid+process;
        String[] timestamp = m.get("timestamp").split("\\.");
        String exePath = m.get("exepath");
        String cmdLine = m.get("cmdLine");
        res[0] = processes.computeIfAbsent(processKey,key ->new ProcessEntity( id, pid,
                null, null, null, timestamp[0], timestamp[1], process, UID++, exePath, cmdLine));

        String args = m.get("args");

        //extract files involved
        Map<String, String> file_socket = ParserUtils.extractFileandSocket(args);
        String process_file = ParserUtils.extractProcessFile(args);


        if(file_socket.containsKey("path")) {
            String path = file_socket.get("path");
            String[] fileArray = path.split("/");
            String fileName = fileArray[fileArray.length - 1];
            res[1] = files.computeIfAbsent(path ,key -> new FileEntity( id, timestamp[0],
                    timestamp[1], null, null, path, UID++, fileName));
        }else if(process_file != null) {
            String[] process_fileArray =  process_file.split("/");
            res[1] = files.computeIfAbsent(process_file,
                    key -> new FileEntity( id, timestamp[0],
                            timestamp[1], null, null, process_file, UID++, process_fileArray[process_fileArray.length - 1]));
        }else if(file_socket.containsKey("sip") && file_socket.containsKey("sport") &&
                file_socket.containsKey("dip") && file_socket.containsKey("dport")) {
            String sourceIP = file_socket.get("sip");
            String sourcePort = file_socket.get("sport");
            String desIP = file_socket.get("dip");
            String desPort = file_socket.get("dport");

            res[1] = networks.computeIfAbsent(sourceIP+":"+sourcePort+"->"+ desIP+":"+desPort,
                    key -> new NetworkEntity( id, timestamp[0], timestamp[1], sourceIP, desIP, sourcePort, desPort, UID++));
        }else res[1] = null;

        return res;
    }

    @Override
    public Map<String, RegistryEntity> getRegistryMap() {
        return null;
    }

    @Override
    public Map<String, PtoREvent> getPtoRMap() {
        return null;
    }

    @Override
    public Map<String, RtoPEvent> getRtoPMap() {
        return null;
    }

    public HashMap<String, PtoFEvent> getPtoFMap() {
        return this.pfEvent;
    }

    public HashMap<String, PtoNEvent> getPtoNMap() {
        return this.pnEvent;
    }

    public HashMap<String, PtoPEvent> getPtoPMap() {
        return this.ppEvent;
    }

    public HashMap<String, NtoPEvent> getNtoPMap() {
        return this.npEvent;
    }

    public HashMap<String, FtoPEvent> getFtoPMap() {
        return this.fpEvent;
    }

    public Map<String, FileEntity> getFileMap() {
    	return this.files;
    }
    
    public Map<String, ProcessEntity> getProcessMap() {
    	return this.processes;
    }

    public Map<String, NetworkEntity> getNetworkMap() {
    	return this.networks;
    }

}
