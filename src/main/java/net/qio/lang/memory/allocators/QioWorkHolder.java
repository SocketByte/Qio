package net.qio.lang.memory.allocators;

import lombok.Getter;
import lombok.Setter;
import net.qio.lang.memory.work.WorkLoop;

import java.util.LinkedList;
import java.util.List;

public class QioWorkHolder {

    @Getter
    private static List<WorkLoop> loopWorks = new LinkedList<>();
    @Getter @Setter
    private static int loopIndex = -1;

}
