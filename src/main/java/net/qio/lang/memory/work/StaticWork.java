package net.qio.lang.memory.work;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class StaticWork {

    @Getter
    private List<IWork> commands = new LinkedList<>();
    @Getter
    private int index = 0;

    public void push(IWork command) {
        commands.add(command);
    }

    public void executeNext() {
        commands.get(index).execute();
        index++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StaticWork that = (StaticWork) o;

        return index == that.index && (commands != null
                ? commands.equals(that.commands) : that.commands == null);
    }

    @Override
    public int hashCode() {
        int result = commands != null ? commands.hashCode() : 0;
        result = 31 * result + index;
        return result;
    }
}
