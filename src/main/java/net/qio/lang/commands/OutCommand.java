package net.qio.lang.commands;

import net.qio.lang.exceptions.SyntaxException;
import net.qio.lang.memory.Function;
import net.qio.lang.memory.QioFunctionAllocator;
import net.qio.lang.memory.QioVariableAllocator;
import net.qio.lang.memory.Variable;
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

        Type type = TypeDetector.detect(syntax);

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
                System.out.println(syntax);
                break;
            case INTEGER:
                System.out.println(syntax);
                break;
            case VARIABLE:
                Variable variable = QioVariableAllocator.pull(syntax);
                System.out.println(QioVariableAllocator.getRealValue(variable));
                break;
            case FUNCTION:
                Object callback = QioFunctionAllocator.getCallback(syntax);
                Object realValue = callback;
                if (callback.toString().startsWith("var@")) {
                    String varName = callback.toString().replace("var@", "");

                    realValue = QioVariableAllocator.getRealValue(QioVariableAllocator.pull(varName));
                }

                System.out.println(realValue);
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
