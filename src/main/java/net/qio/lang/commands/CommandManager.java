package net.qio.lang.commands;

import lombok.Getter;
import net.qio.lang.exceptions.SyntaxException;
import net.qio.lang.utilities.types.Keyword;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    @Getter
    private static final Map<Keyword, Command> commands = new HashMap<>();

    public static void add(Command command) {
        commands.put(command.getKeyword(), command);
    }

    public static Command get(Keyword keyword) {
        return commands.get(keyword);
    }

    public static Command get(String keyword) {
        return commands.get(Keyword.valueOf(keyword));
    }

    public static boolean contains(String keyword) {
        try {
            return commands.containsKey(Keyword.valueOf(keyword));
        } catch (Exception e) {
            try {
                throw new SyntaxException("Keyword " + keyword + " not found.");
            } catch (SyntaxException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }

}
