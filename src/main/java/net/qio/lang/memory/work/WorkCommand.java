package net.qio.lang.memory.work;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.qio.lang.commands.Command;

@Getter
@Setter
@ToString
public class WorkCommand implements IWork {

    private Command command;
    private int tabs;
    private String syntax;

    public WorkCommand(Command command, int tabs, String syntax) {
        this.command = command;
        this.tabs = tabs;
        this.syntax = syntax;
    }

    @Override
    public void execute() {
        command.execute(tabs, syntax);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkCommand that = (WorkCommand) o;

        return tabs == that.tabs
                && (command != null ? command.equals(that.command) : that.command == null)
                && (syntax != null ? syntax.equals(that.syntax) : that.syntax == null);
    }

    @Override
    public int hashCode() {
        int result = command != null ? command.hashCode() : 0;
        result = 31 * result + tabs;
        result = 31 * result + (syntax != null ? syntax.hashCode() : 0);
        return result;
    }
}
