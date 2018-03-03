package net.qio.lang;

import lombok.Getter;
import lombok.Setter;
import net.qio.lang.commands.Command;
import net.qio.lang.commands.CommandManager;
import net.qio.lang.commands.LoopStartCommand;
import net.qio.lang.exceptions.SyntaxException;
import net.qio.lang.memory.allocators.QioFunctionAllocator;
import net.qio.lang.memory.allocators.QioVariableAllocator;
import net.qio.lang.memory.allocators.QioVariableHolder;
import net.qio.lang.memory.allocators.QioWorkHolder;
import net.qio.lang.memory.temp.FunctionTempContainer;
import net.qio.lang.memory.temp.LoopTempContainer;
import net.qio.lang.memory.work.StaticWork;
import net.qio.lang.memory.work.Work;
import net.qio.lang.memory.work.WorkCommand;
import net.qio.lang.memory.work.WorkLoop;
import net.qio.lang.splitters.EqualSplitter;
import net.qio.lang.utilities.types.Keyword;
import net.qio.lang.utilities.StringUtilities;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class QioInterpreter {

    @Getter
    private static int currentLine = 0;
    @Getter
    private static String lastLine = null;
    @Getter @Setter
    private static FunctionTempContainer container = new FunctionTempContainer();
    @Getter @Setter
    private static LoopTempContainer loopContainer = new LoopTempContainer();
    @Getter @Setter
    private static StaticWork staticWork = new StaticWork();

    public static void interpret(String[] lines) {
        QioVariableAllocator.getVariables().clear();
        QioFunctionAllocator.getFunctions().clear();
        QioVariableHolder.getVariableList().clear();
        QioWorkHolder.getLoopWorks().clear();
        currentLine = 0;
        lastLine = null;
        container.clear();
        loopContainer.clear();
        QioWorkHolder.setLoopIndex(-1);
        System.gc();
        System.out.println("Qio Alpha v2.8 running...");
        System.out.println();
        for (String line : lines) {
            if (line.startsWith("#"))
                continue;
            if (line.isEmpty())
                continue;

            // Remove comments
            line = StringUtils.substringBefore(line, "#");

            String[] split = line.split(" ");
            int tabs = StringUtilities.getTabs(split[0]);
            String command = StringUtilities.removeTabs(split[0]);
            String syntax = StringUtils.join(split, " ", 1, split.length);

            if (!CommandManager.contains(command))
                return;

            Command commandInstance = CommandManager.get(command);
            if (commandInstance.getKeyword().equals(Keyword.FUNC_END)) {
                commandInstance.execute(tabs, syntax);
            }
            else if (container.isFunctionCall() && !commandInstance.getKeyword().equals(Keyword.FUNC_END)) {
                if (container.getCurrentWork() == null)
                    container.setCurrentWork(new Work());

                if (commandInstance.getKeyword().equals(Keyword.PUSH)) {
                    EqualSplitter<String, String> splitter = null;
                    try {
                        splitter = new EqualSplitter<>(syntax);
                    } catch (SyntaxException e) {
                        e.printStackTrace();
                    }
                    String first = splitter.getFirstPart();

                    QioVariableHolder.getVariableList().add(first);
                }
                if (!loopContainer.isLoopCall()) {
                    container.getCurrentWork().push(new WorkCommand(commandInstance, tabs, syntax));
                }
            }
            else if (!loopContainer.isLoopCall() && !container.isFunctionCall()) {
                commandInstance.execute(tabs, syntax);
            }

            if (commandInstance.getKeyword().equals(Keyword.LOOP_END) && loopContainer.isLoopCall()) {
                WorkLoop workLoop = new WorkLoop(loopContainer);
                QioWorkHolder.getLoopWorks().add(workLoop);

                if (container != null && container.isFunctionCall())
                    container.getCurrentWork().push(workLoop);

                QioInterpreter.loopContainer = new LoopTempContainer();
            }
            if (loopContainer.isLoopCall() && !commandInstance.getKeyword().equals(Keyword.LOOP_END)) {
                if (loopContainer.getCurrentWork() == null)
                    loopContainer.setCurrentWork(new Work());

                loopContainer.getCurrentWork().push(new WorkCommand(commandInstance, tabs, syntax));
            }

            if (commandInstance.getKeyword().equals(Keyword.LOOP_START)) {
                QioInterpreter.getLoopContainer().setLoopCall(true);
            }
            else if (commandInstance.getKeyword().equals(Keyword.LOOP_END)) {
                QioInterpreter.getLoopContainer().setLoopCall(false);
                staticWork.executeNext();
            }

            currentLine++;

        }
    }

}
