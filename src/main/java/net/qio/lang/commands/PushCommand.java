package net.qio.lang.commands;

import net.qio.lang.QioInterpreter;
import net.qio.lang.exceptions.SyntaxException;
import net.qio.lang.memory.Function;
import net.qio.lang.memory.allocators.QioFunctionAllocator;
import net.qio.lang.memory.allocators.QioVariableAllocator;
import net.qio.lang.memory.Variable;
import net.qio.lang.memory.allocators.QioWorkHolder;
import net.qio.lang.memory.temp.LoopTempContainer;
import net.qio.lang.memory.work.WorkLoop;
import net.qio.lang.splitters.EqualSplitter;
import net.qio.lang.utilities.MathUtilities;
import net.qio.lang.utilities.StringUtilities;
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
        EqualSplitter<String, String> splitter = null;
        try {
            splitter = new EqualSplitter<>(syntax);
        } catch (SyntaxException e) {
            e.printStackTrace();
        }
        String first = splitter.getFirstPart();
        String second = splitter.getSecondPart();
        Type type1 = TypeDetector.detect(first, false);
        Type type2 = TypeDetector.detect(second, true);

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
                    String parsed = StringUtilities.parse(second);
                    Object val = parsed;
                    try {
                        String condition = StringUtilities.conditionPush(parsed);
                        if (condition != null) {
                            val = TypeDetector.convertToValue(TypeDetector.detect(condition, true), condition);
                        }
                    } catch (Exception ignored) {
                    }
                    //new PushCommand().execute(tabs, "PUSH " + first + " = " + val);
                    QioVariableAllocator.set(variable, val);
                    break;
                case INTEGER:
                    try {
                        String replaced = second;
                        for (Variable variable1 : QioVariableAllocator.getVariables().values()) {
                            replaced = replaced.replace(variable1.getName(),
                                    QioVariableAllocator.getRealValue(variable1).toString());
                        }
                        double value = MathUtilities.eval(replaced);

                        QioVariableAllocator.set(variable, value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
                    String parsed = StringUtilities.parse(second);
                    Object val = parsed;
                    String condition = StringUtilities.conditionPush(parsed);
                    if (condition != null) {
                        val = TypeDetector.convertToValue(TypeDetector.detect(condition, true), condition);
                    }
                    //new PushCommand().execute(tabs, "PUSH " + first + " = " + val);
                    QioVariableAllocator.createVariable(first, val);
                    break;
                case INTEGER:
                    try {
                        String replaced = second;
                        for (Variable variable1 : QioVariableAllocator.getVariables().values()) {
                            replaced = replaced.replace(variable1.getName(),
                                    QioVariableAllocator.getRealValue(variable1).toString());
                        }
                        double value = MathUtilities.eval(replaced);

                        QioVariableAllocator.createVariable(first, value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
    }
}
