package net.qio.lang;

import net.qio.lang.commands.*;
import net.qio.lang.utilities.StringUtilities;

public class Qio {

    public Qio() {
        initialize();
    }

    private void initialize() {
        CommandManager.add(new PushCommand());
        CommandManager.add(new OutCommand());
        CommandManager.add(new FuncStartCommand());
        CommandManager.add(new FuncEndCommand());
        CommandManager.add(new ExecuteCommand());
        CommandManager.add(new LoopStartCommand());
        CommandManager.add(new LoopEndCommand());
    }

    public static void main(String[] args) {
        StringUtilities.condition("EXECUTE 5 > 3 ? YES % NO");
    }

}
