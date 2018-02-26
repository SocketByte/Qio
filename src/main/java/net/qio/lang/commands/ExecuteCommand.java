package net.qio.lang.commands;

import net.qio.lang.memory.QioFunctionAllocator;
import net.qio.lang.utilities.types.Keyword;

public class ExecuteCommand extends Command {
    public ExecuteCommand() {
        super(Keyword.EXECUTE);
    }

    @Override
    public void execute(int tabs, String syntax) {
        QioFunctionAllocator.invoke(syntax);
    }
}
