package net.qio.lang;

import net.qio.lang.commands.*;

public class Qio {

    public Qio() {
        CommandManager.add(new PushCommand());
        CommandManager.add(new OutCommand());
        CommandManager.add(new FuncStartCommand());
        CommandManager.add(new FuncEndCommand());
        CommandManager.add(new ExecuteCommand());
    }

}
