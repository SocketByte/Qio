package net.qio.lang;

import jdk.nashorn.internal.runtime.regexp.joni.Syntax;
import lombok.Getter;
import lombok.Setter;
import net.qio.lang.commands.Command;
import net.qio.lang.commands.CommandManager;
import net.qio.lang.exceptions.SyntaxException;
import net.qio.lang.memory.*;
import net.qio.lang.splitters.EqualSplitter;
import net.qio.lang.utilities.types.Keyword;
import net.qio.lang.utilities.StringUtilities;
import org.apache.commons.lang3.StringUtils;

public class QioInterpreter {

    @Getter
    private static int currentLine = 0;
    @Getter
    private static String lastLine = null;
    @Getter @Setter
    private static FunctionTempContainer container = new FunctionTempContainer();

    public static void interpret(String[] lines) throws SyntaxException {
        System.out.println("Garbage collector...");
        QioVariableAllocator.getVariables().clear();
        System.gc();
        System.out.println("Qio v2.8 running...");
        System.out.println();
        for (String line : lines) {
            String[] split = line.split(" ");
            int tabs = StringUtilities.getTabs(split[0]);
            String command = StringUtilities.removeTabs(split[0]);
            String syntax = StringUtils.join(split, " ", 1, split.length);

            if (!CommandManager.contains(command))
                throw new SyntaxException("Command not found.");

            Command commandInstance = CommandManager.get(command);
            if (container.isFunctionCall() && !commandInstance.getKeyword().equals(Keyword.FUNC_END)) {
                if (container.getCurrentWork() == null)
                    container.setCurrentWork(new FunctionWork());

                if (commandInstance.getKeyword().equals(Keyword.PUSH)) {
                    EqualSplitter<String, String> splitter = new EqualSplitter<>(syntax);
                    String first = splitter.getFirstPart();

                    QioVariableHolder.getVariableList().add(first);
                }
                container.getCurrentWork().push(new FunctionCommand(commandInstance, tabs, syntax));
            }
            else if (commandInstance.getKeyword().equals(Keyword.FUNC_END)) {
                commandInstance.execute(tabs, syntax);
            }
            else {
                commandInstance.execute(tabs, syntax);
            }

            currentLine++;

        }
    }

}
