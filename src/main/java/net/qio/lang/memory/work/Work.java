package net.qio.lang.memory.work;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class Work {

    private List<IWork> commands = new LinkedList<>();

    public void push(IWork command) {
        commands.add(command);
    }

    public void execute() {
        for (IWork command : commands)
            command.execute();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Work that = (Work) o;

        return commands != null ? commands.equals(that.commands) : that.commands == null;
    }

    @Override
    public int hashCode() {
        return commands != null ? commands.hashCode() : 0;
    }
}
