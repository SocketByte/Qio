package net.qio.lang.commands;

import net.qio.lang.memory.allocators.QioFunctionAllocator;
import net.qio.lang.utilities.StringUtilities;
import net.qio.lang.utilities.types.Keyword;

public class ExecuteCommand extends Command {
    public ExecuteCommand() {
        super(Keyword.EXECUTE);
    }

    @Override
    public void execute(int tabs, String syntax) {
        try {
            StringUtilities.condition(syntax);
        } catch (Exception e) {
            QioFunctionAllocator.invoke(syntax);
        }
    }
}
