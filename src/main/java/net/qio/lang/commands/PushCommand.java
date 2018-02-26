package net.qio.lang.commands;

import net.qio.lang.exceptions.SyntaxException;
import net.qio.lang.memory.Function;
import net.qio.lang.memory.QioFunctionAllocator;
import net.qio.lang.memory.QioVariableAllocator;
import net.qio.lang.memory.Variable;
import net.qio.lang.splitters.EqualSplitter;
import net.qio.lang.utilities.types.Keyword;
import net.qio.lang.utilities.types.Type;
import net.qio.lang.utilities.TypeDetector;

public class PushCommand extends Command {
    public PushCommand() {
        super(Keyword.PUSH);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute(int tabs, String syntax) {
        EqualSplitter<String, String> splitter = new EqualSplitter<>(syntax);
        String first = splitter.getFirstPart();
        String second = splitter.getSecondPart();
        Type type1 = TypeDetector.detect(first);
        Type type2 = TypeDetector.detect(second);

        if (type1 == null || type2 == null) {
            try {
                throw new SyntaxException("Invalid syntax. Something went wrong, check your spelling.");
            } catch (SyntaxException e) {
                e.printStackTrace();
            }
            return;
        }

        if (type1 == Type.VARIABLE) {
            Variable variable = QioVariableAllocator.pull(first);
            switch (type2) {
                case VARIABLE:
                    Variable variable2 = QioVariableAllocator.pull(second);

                    QioVariableAllocator.createReference(first, variable2);
                    break;
                case STRING:
                    QioVariableAllocator.set(variable, second);
                    break;
                case INTEGER:
                    QioVariableAllocator.set(variable, Integer.valueOf(second));
                    break;
                case FUNCTION: {
                    Function function = QioFunctionAllocator.pull(second);
                    QioVariableAllocator.set(variable, function.getCallback());
                    break;
                }
                case VOID:
                    try {
                        throw new SyntaxException("Void is not allowed here.");
                    } catch (SyntaxException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        else if (type1 == Type.STRING) {
            switch (type2) {
                case VARIABLE:
                    Variable variable2 = QioVariableAllocator.pull(second);

                    QioVariableAllocator.createReference(first, variable2);
                    break;
                case STRING:
                    QioVariableAllocator.createVariable(first, second);
                    break;
                case INTEGER:
                    QioVariableAllocator.createVariable(first, Integer.valueOf(second));
                    break;
                case FUNCTION: {
                    Function function = QioFunctionAllocator.pull(second);
                    QioVariableAllocator.createVariable(first, function.getCallback());
                    break;
                }
                case VOID:
                    try {
                        throw new SyntaxException("Void is not allowed here.");
                    } catch (SyntaxException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        else if (type1 == Type.INTEGER) {
            try {
                throw new SyntaxException("Invalid syntax. You can not assign value to an integer!");
            } catch (SyntaxException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("null");
        }
    }
}
