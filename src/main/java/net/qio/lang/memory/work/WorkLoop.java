package net.qio.lang.memory.work;

import lombok.Getter;
import net.qio.lang.memory.temp.LoopTempContainer;

public class WorkLoop implements IWork {

    @Getter
    private LoopTempContainer loopContainer;

    public WorkLoop(LoopTempContainer container) {
        this.loopContainer = container;
    }

    @Override
    public String toString() {
        return String.valueOf(loopContainer.getCurrentLoopCount());
    }

    @Override
    public void execute() {
        int loops = loopContainer.getLoops();
        for (int i = 0; i < loops; i++) {
            loopContainer.setCurrentLoopCount(i);
            loopContainer.getCurrentWork().execute();
        }
        loopContainer.clear();
    }
}
