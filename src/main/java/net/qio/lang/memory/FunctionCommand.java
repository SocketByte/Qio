package net.qio.lang.memory;

import lombok.Getter;
import lombok.Setter;
import net.qio.lang.commands.Command;

@Getter
@Setter
public class FunctionCommand {

    private Command command;
    private int tabs;
    private String syntax;

    public FunctionCommand(Command command, int tabs, String syntax) {
        this.command = command;
        this.tabs = tabs;
        this.syntax = syntax;
    }

    public void execute() {
        command.execute(tabs, syntax);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FunctionCommand that = (FunctionCommand) o;

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
