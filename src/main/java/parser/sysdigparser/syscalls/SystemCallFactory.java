package parser.sysdigparser.syscalls;

import java.util.ArrayList;
import java.util.List;

public class SystemCallFactory {
    private static long id = 0;
    private String type;
    private List<Action> onMatch;
    private Class cStart;
    private Class cEnd;

    public SystemCallFactory(String type, Class c1, Class c2){
        this.type = type;
        this.onMatch = new ArrayList<>();
        cStart = c1;
        cEnd = c2;
    }

    public SystemCallFactory addAction(Action action){
        onMatch.add(action);
        return this;
    }

    public SystemCall getSystemCall(String event) {
        SystemCall instance = new SystemCall(""+id++, type,
                new Fingerprint(event, cStart, cEnd));
        for(Action action : onMatch)
            instance = instance.addAction(action);
        return instance;
    }
}
