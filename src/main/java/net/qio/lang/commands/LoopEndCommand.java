package net.qio.lang.commands;

import net.qio.lang.utilities.types.Keyword;

public class LoopEndCommand extends Command {
    public LoopEndCommand() {
        super(Keyword.LOOP_END);
    }

    @Override
    public void execute(int tabs, String syntax) {

    }
}
