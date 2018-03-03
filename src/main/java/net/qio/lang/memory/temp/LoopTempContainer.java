package net.qio.lang.memory.temp;

import lombok.Data;
import net.qio.lang.memory.work.Work;

@Data
public class LoopTempContainer {

    private int loops;
    private int currentLoopCount;
    private Work currentWork;

    private boolean loopCall;

    public void clear() {
        this.loopCall = false;
        this.loops = 0;
        this.currentLoopCount = 0;
        this.currentWork = null;
    }

    @Override
    public String toString() {
        return String.valueOf(getCurrentLoopCount());
    }

}
