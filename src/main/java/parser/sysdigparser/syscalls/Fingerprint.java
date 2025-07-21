package parser.sysdigparser.syscalls;


import datamodel.Entity;

import java.util.Map;

public class Fingerprint {
    private String fingerprintString;

    public Fingerprint(String s,Class c1,Class c2){
        String s1 = c1==null?"null":""+c1.toString();
        String s2 = c2==null?"null":""+c2.toString();
        fingerprintString = s+s1+s2;
    }

    public static Fingerprint toFingerPrint(Map<String, String> begin, Map<String, String> end, Entity[] beginEntity, Entity[] endEntity){
        Class c1 = beginEntity[1]==null?null:beginEntity[1].getClass();
        Class c2 = endEntity[1]==null?null:endEntity[1].getClass();
        return new Fingerprint(begin.get("event"),c1,c2);
    };

    public String getFingerprintString(){
        return fingerprintString;
    }

    @Override
    public int hashCode(){
        return fingerprintString.hashCode();
    }

    @Override
    public boolean equals(Object o){
        return o instanceof Fingerprint && ((Fingerprint)o).getFingerprintString().equals(fingerprintString);
    }

    @Override
    public String toString() {
        return fingerprintString;
    }
}
