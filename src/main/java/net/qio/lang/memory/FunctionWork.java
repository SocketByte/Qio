package net.qio.lang.memory;

import java.util.LinkedList;
import java.util.List;

public class FunctionWork {

    private List<FunctionCommand> commands = new LinkedList<>();

    public void push(FunctionCommand command) {
        commands.add(command);
    }

    public void execute() {
        for (FunctionCommand command : commands)
            command.execute();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FunctionWork that = (FunctionWork) o;

        return commands != null ? commands.equals(that.commands) : that.commands == null;
    }

    @Override
    public int hashCode() {
        return commands != null ? commands.hashCode() : 0;
    }
}
