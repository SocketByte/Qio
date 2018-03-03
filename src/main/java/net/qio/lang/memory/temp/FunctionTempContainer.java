package net.qio.lang.memory.temp;

import lombok.Data;
import net.qio.lang.memory.work.Work;

@Data
public class FunctionTempContainer {

    private String name;
    private Class<?> type;
    private Work currentWork;

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
