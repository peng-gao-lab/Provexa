package config;

public class MetaConfig {
    public static final String[] ptopSystemCall = {"execve"};
    public static final String[] ptofSystemCall ={"write","writev"};
    public static final String[] ftopSystemCall = {"read","readv"};
    public static final String[] ptonSystemCall = {"sendto","write","writev"};
    public static final String[] ntopSystemCall = {"read", "recvmsg", "recvfrom","readv"};

    public static final String[] ptopOperation = {"Process Create"};
    public static final String[] ptofOperation = {"WriteFile", "CreateFile", "CloseFile", "SetRenameInformationFile", "SetRenameInformationFile"};
    public static final String[] ftopOperation = {"ReadFile", "Load Image"};
    public static final String[] ptonOperation = {"TCP Send", "TCP Connect"};
    public static final String[] ntopOperation = {"TCP Receive"};
    public static final String[] ptorOperation = {"RegSetValue"};
    public static final String[] rtopOperation = {};

    public static final String windowsPathRegex = "(.*)\\\\(.*)";

    public static final String hostName = "default_host";
}