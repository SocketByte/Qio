package net.qio.lang.memory;

import lombok.Data;

@Data
public class FunctionTempContainer {

    private String name;
    private Class<?> type;
    private FunctionWork currentWork;

    private boolean functionCall;

    public void clear() {
        this.name = null;
        this.type = null;
        this.currentWork = null;
        this.functionCall = false;
    }

    @Override
    public String toString() {
        return "FunctionTempContainer{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", currentWork=" + currentWork +
                ", functionCall=" + functionCall +
                '}';
    }
}
