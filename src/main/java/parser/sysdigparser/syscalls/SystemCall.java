package parser.sysdigparser.syscalls;


import datamodel.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SystemCall {
    public String name;
    public String type;
    public Fingerprint fingerPrint;
    public List<Action> onMatch;

    public SystemCall(String name, String type, Fingerprint fingerPrint){
        this.name = name;
        this.type = type;
        this.fingerPrint = fingerPrint;
        this.onMatch = new ArrayList<>();
    }

    public SystemCall addAction(Action action){
        onMatch.add(action);
        return this;
    }

    public void react(Map<String, String> mStart, Map<String, String> mEnd, Entity[] eStart, Entity[] eEnd){
        for(Action action:onMatch)
            action.apply(mStart,mEnd,eStart,eEnd);
    }

}
