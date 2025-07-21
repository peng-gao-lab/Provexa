package parser.sysdigparser.syscalls;

import datamodel.Entity;
import java.util.Map;

public interface Action {
    void apply(Map<String, String> begin, Map<String, String> end, Entity[] startEntities, Entity[] endEntities);

}
