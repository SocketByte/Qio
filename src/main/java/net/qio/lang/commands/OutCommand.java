package net.qio.lang.commands;

import net.qio.lang.QioInterpreter;
import net.qio.lang.exceptions.SyntaxException;
import net.qio.lang.memory.allocators.QioFunctionAllocator;
import net.qio.lang.memory.allocators.QioVariableAllocator;
import net.qio.lang.memory.Variable;
import net.qio.lang.memory.allocators.QioWorkHolder;
import net.qio.lang.memory.temp.LoopTempContainer;
import net.qio.lang.memory.work.WorkLoop;
import net.qio.lang.utilities.MathUtilities;
import net.qio.lang.utilities.PrintUtilities;
import net.qio.lang.utilities.StringUtilities;
import net.qio.lang.utilities.types.Keyword;
import net.qio.lang.utilities.types.Type;
import net.qio.lang.utilities.TypeDetector;

public class OutCommand extends Command {

    public OutCommand() {
        super(Keyword.OUT);
    }

    @Override
    public void execute(int tabs, String syntax) {
        if (syntax.equals("$variables$")) {
            QioVariableAllocator.printVariables();
            return;
        }
        else if (syntax.startsWith("$typeof:") && (syntax.endsWith("$"))) {
            String[] split = syntax.split(":");
            String var = split[1].replace("$", "");

            PrintUtilities.println(QioVariableAllocator.pull(var).getType());
            return;
        }

        Type type = TypeDetector.detect(syntax, true);

        syntax = syntax.replace("\"", "");

        if (type == null) {
            try {
                throw new SyntaxException("Invalid syntax. Something went wrong, check your spelling.");
            } catch (SyntaxException e) {
                e.printStackTrace();
            }
            return;
        }
        switch (type) {
            case STRING:
                try {
                    PrintUtilities.println(StringUtilities.parse(StringUtilities.conditionPush(syntax)));
                } catch (Exception ignored) {
                    PrintUtilities.println(StringUtilities.parse(syntax));
                }
                break;
            case INTEGER:
                String replaced = syntax;
                for (Variable variable1 : QioVariableAllocator.getVariables().values()) {
                    replaced = replaced.replace(variable1.getName(),
                            QioVariableAllocator.getRealValue(variable1).toString());
                }
                double value = MathUtilities.eval(replaced);

                PrintUtilities.println(value);
                break;
            case VARIABLE:
                Variable variable = QioVariableAllocator.pull(syntax);
                PrintUtilities.println(QioVariableAllocator.getRealValue(variable));
                break;
            case FUNCTION:
                Object callback = QioFunctionAllocator.getCallback(syntax);
                Object realValue = callback;
                if (callback.toString().startsWith("var@")) {
                    String varName = callback.toString().replace("var@", "");

                    realValue = QioVariableAllocator.getRealValue(QioVariableAllocator.pull(varName));
                }

                PrintUtilities.println(realValue);
                break;
            case VOID:
                try {
                    throw new SyntaxException("You can not print void methods.");
                } catch (SyntaxException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
