package parser;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ParserUtils {
    public static Map<String, String> parseEntryWithoutID(String entry) {
        Map<String, String> res = new HashMap<>();
        String[] fields = entry.split(" ");
        res.put("raw",entry);
        res.put("timestamp",fields[0]);
        res.put("cpu",fields[1]);
        res.put("process",fields[2]);
        res.put("pid",fields[3].substring(1,fields[3].length()-1));
        res.put("direction", fields[4]);
        res.put("event", fields[5]);
        res.put("cwd", fields[6].substring(4));
        res.put("latency", fields[fields.length-1].substring(8));
        res.put("args", String.join(" ", Arrays.copyOfRange(fields,7,fields.length-2)));
        return res;
    }

    public static Map<String, String> parseEntry(String entry) {
        Map<String, String> res = new HashMap<>();
        try {
            String[] fields = entry.trim().split(" ");
            res.put("raw", entry);

            // Basic fields (fixed positions)
            res.put("eventno", fields[0]);
            res.put("timestamp", fields[1]);
            res.put("cpu", fields[2]);
            res.put("process", fields[3]);

            // PID (remove parentheses)
            String pidField = fields[4];
            if (!pidField.matches("\\(\\d+\\)")) {
                // System.err.println("Malformed PID field: " + pidField + " in entry: " + entry);
                res.put("pid", "-1"); // fallback value
            } else {
                res.put("pid", pidField.substring(1, pidField.length() - 1));
            }

            res.put("direction", fields[5]);
            res.put("event", fields[6]);

            // CWD extraction
            if (fields[7].startsWith("cwd=")) {
                res.put("cwd", fields[7].substring(4));
            } else {
                res.put("cwd", "");
            }

            // Scan for latency=, exepath=, cmd= and collect their indices
            int latencyIndex = -1;
            int exepathIndex = -1;
            int cmdIndex = -1;

            for (int i = 8; i < fields.length; i++) {
                if (fields[i].startsWith("latency=")) {
                    latencyIndex = i;
                } else if (fields[i].startsWith("exepath=")) {
                    exepathIndex = i;
                } else if (fields[i].equals("cmd=")) {
                    cmdIndex = i;
                    break; // cmd= is the start of final command line
                }
            }

            // Fill in optional fields
            res.put("latency", latencyIndex != -1 ? fields[latencyIndex].substring(8) : "");
            res.put("exepath", exepathIndex != -1 ? fields[exepathIndex].substring(8) : "");

            // Extract arguments (between cwd and latency)
            int argsEnd = (latencyIndex != -1 ? latencyIndex : (exepathIndex != -1 ? exepathIndex : cmdIndex));
            if (argsEnd == -1) argsEnd = fields.length;
            if (argsEnd > 8) {
                res.put("args", String.join(" ", Arrays.copyOfRange(fields, 8, argsEnd)));
            } else {
                res.put("args", "");
            }

            // Extract cmdLine (everything after "cmd=")
            if (cmdIndex != -1 && cmdIndex + 1 < fields.length) {
                res.put("cmdLine", String.join(" ", Arrays.copyOfRange(fields, cmdIndex + 1, fields.length)));
            } else {
                res.put("cmdLine", "");
            }
        } catch (Exception e) {
            // System.err.println("Failed to parse entry: " + entry);
            return null;
        }
        return res;
    }


    public static Map<String, String> parseEntryOld(String entry) {
        Map<String, String> res = new HashMap<>();
        String[] fields = entry.split(" ");
        int pidIndex = -1;
        int cmdIndex = -1;
        for(int i=0;i<fields.length;i++) {
        	if(pidIndex == -1 && fields[i].contains("(")) {
        		pidIndex = i;
        	}
        	if(fields[i].equals("cmd=")) {
        	    cmdIndex = i;
        	    break;
            }
        }
        if (cmdIndex == -1) {
            cmdIndex = fields.length;
        } else {
            res.put("cmdLine", String.join(" ", Arrays.copyOfRange(fields, cmdIndex+1, fields.length)));
        }
        String[] processArr = Arrays.copyOfRange(fields, 3, pidIndex);
        String processStr = String.join(" ", processArr);
        res.put("raw",entry);
        res.put("eventno", fields[0]);
        res.put("timestamp",fields[1]);
        res.put("cpu",fields[2]);
        res.put("process",processStr);
        res.put("pid",fields[pidIndex].substring(1,fields[pidIndex].length()-1));
        res.put("direction", fields[pidIndex+1]);
        res.put("event", fields[pidIndex+2]);
        res.put("cwd", fields[pidIndex+3].substring(4));
        try {
            res.put("latency", fields[cmdIndex - 2].substring(8));
            res.put("exepath", fields[cmdIndex - 1].substring(8));
            res.put("args", String.join(" ", Arrays.copyOfRange(fields,pidIndex+4,cmdIndex - 2)));
        } catch (IllegalArgumentException e) {
            System.out.println(entry);
        }
        return res;
    }

//PID, Process Name, Image Path, Path, Operation, Detail, Date & Time, Time of Day, Duration, Completion Time, Event Class, Result, Parent PID
    public static Map<String, String> parseCsvLine(String csvLine) {
        Map<String, String> res = new HashMap<>();
        String[] fields = csvLine.split("\",\"");
        int pidIndex = -1;
        String[] processArr = Arrays.copyOfRange(fields, 3, pidIndex);
        String processStr = String.join(" ", processArr);
        res.put("raw",csvLine);
        res.put("pid", fields[0]);
        res.put("process", fields[1]);
        res.put("exepath", fields[2]);
        res.put("object", fields[3]);
        res.put("event", fields[4]);
        res.put("details", fields[5]);
        res.put("latency", fields[8]);
        //TODO process the time
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSSSSSS a");
        try {
            String timeString = fields[6].split(" ")[0] + fields[7];
            Date parsedDate = dateFormat.parse(timeString);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            res.put("timestamp", timestamp.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        res.put("result", fields[11]);
        return res;
    }

    public static long extractSize(String s) {
        if(s.contains("res=")) {
            String size = s.substring(s.indexOf("res=")+4).split(" ")[0];
            if (!size.startsWith("-"))
                return Long.parseLong(size);
        }
        return -1L;
    }

    public static Map<String, String> extractFileandSocket(String s) {
        String path = null;
        String srcIP = null;
        String srcPort = null;
        String destIP = null;
        String destPort = null;
        String socket = null;

        Map<String, String> res = new HashMap<>();

        if(s.contains("fd=")) {
            String fd = s.substring(s.indexOf("fd=")).split(" ")[0];
            int index = 0;
            while (index < fd.length()) {
                if (fd.charAt(index) == '>') {
                    if (fd.charAt(index - 1) == 'f') {
                        path = fd.substring(index + 1, fd.length() - 1);
                        if(path.contains("("))
                            path = path.substring(path.indexOf("(")+1,path.length()-1);
                        break;
                    }
                    if (fd.charAt(index - 1) == 't' || fd.charAt(index - 1) == 'u') {
                        if (fd.charAt(index - 2) == '6' || fd.charAt(index - 2) == '4') {
                            socket = fd.substring(index + 1, fd.length() - 1);
                            if (!socket.equals("")) {
                                String[] portsAndIp = getIPandPorts(socket);             //0:src ip 1: src port 2:dest ip 3:dest port
                                srcIP = portsAndIp[0];
                                srcPort = portsAndIp[1];
                                destIP = portsAndIp[2];
                                destPort = portsAndIp[3];
                                break;
                            }
                        }
                    }
                }
                index++;
            }
        }

        if(path != null) res.put("path", path);
        if(srcIP != null) res.put("sip", srcIP);
        if(srcPort != null) res.put("sport", srcPort);
        if(destIP != null) res.put("dip", destIP);
        if(destPort != null) res.put("dport", destPort);
        if(socket != null && !socket.equals("")) res.put("socket", socket);
        return res;
    }

    public static String extractProcessFile(String s) {
        String path = null;
        if(s.contains("filename=")){
            path = s.substring(s.indexOf("filename=")+9).split(" ")[0];
            if(path.contains("("))
                path = path.substring(path.indexOf("(")+1,path.length()-1);
        }
        return path;
    }

    private static String[] getIPandPorts(String str){
        String[] res = new String[4];
        String[] srcAndDest = str.split("->");
        if(srcAndDest.length<2){
            throw new ArrayIndexOutOfBoundsException("Can't parse socket!");
        }
        String[] src = srcAndDest[0].split(":");
        String[] dest = srcAndDest[1].split(":");
        res[0] = src[0];
        res[1] = src[1];
        res[2] = dest[0];
        res[3] = dest[1];
        return res;
    }
}
