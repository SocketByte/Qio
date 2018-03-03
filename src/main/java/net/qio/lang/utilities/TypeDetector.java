package net.qio.lang.utilities;

import net.qio.lang.exceptions.SyntaxException;
import net.qio.lang.memory.allocators.QioFunctionAllocator;
import net.qio.lang.memory.allocators.QioVariableAllocator;
import net.qio.lang.memory.Variable;
import net.qio.lang.utilities.types.Type;

public class TypeDetector {

    public static Object convertToValue(Type type, String syntax) {
        Object val = null;
        switch (type) {
            case STRING:
                val = StringUtilities.parse(syntax);
                break;
            case INTEGER:
                String replaced = syntax;
                for (Variable variable1 : QioVariableAllocator.getVariables().values()) {
                    replaced = replaced.replaceAll("\\b" + variable1.getName() + "\\b",
                            QioVariableAllocator.getRealValue(variable1).toString());
                }

                val = MathUtilities.eval(replaced);
                break;
            case VARIABLE:
                Variable variable = QioVariableAllocator.pull(syntax);
                val = QioVariableAllocator.getRealValue(variable);
                break;
            case FUNCTION:
                Object callback = QioFunctionAllocator.getCallback(syntax);
                Object realValue = callback;
                if (callback.toString().startsWith("var@")) {
                    String varName = callback.toString().replace("var@", "");

                    realValue = QioVariableAllocator.getRealValue(QioVariableAllocator.pull(varName));
                }

                val = realValue;
                break;
            case BOOLEAN:
                val = syntax;
                break;
            case VOID:
                try {
                    throw new SyntaxException("You can not print void methods.");
                } catch (SyntaxException e) {
                    e.printStackTrace();
                }
                break;
        }
        return val;
    }

    public static Type detect(String syntax, boolean val) {
        if (syntax == null) {
            return Type.VOID;
        }
        else if (syntax.equals("true") || syntax.equals("false")) {
            return Type.BOOLEAN;
        }
        else if (QioVariableAllocator.containsVariable(syntax)) {
            return Type.VARIABLE;
        }
        else if (QioFunctionAllocator.containsFunction(syntax)) {
            return Type.FUNCTION;
        }
        else if (syntax.equalsIgnoreCase("void")) {
            return Type.VOID;
        }
        else {
            try {
                double value = 0;
                if (val) {
                    String replaced = syntax;
                    for (Variable variable1 : QioVariableAllocator.getVariables().values()) {
                        replaced = replaced.replaceAll("\\b" + variable1.getName() + "\\b",
                                QioVariableAllocator.getRealValue(variable1).toString());
                    }
                    value = MathUtilities.eval(replaced);
                }
                else {
                    value = MathUtilities.eval(syntax);
                }
                if (value == -Double.MAX_VALUE) {
                    return Type.STRING;
                }
                return Type.INTEGER;
            } catch (Exception e) {
                return Type.STRING;
            }
        }
    }

    public static Object detectObject(String syntax) {
        if (QioVariableAllocator.containsVariable(syntax)) {
            return QioVariableAllocator.pull(syntax);
        }
        else if (QioFunctionAllocator.containsFunction(syntax)) {
            return QioFunctionAllocator.pull(syntax);
        }
        else if (syntax.equalsIgnoreCase("void")) {
            return null;
        }
        else {
            try {
                return Integer.parseInt(syntax);
            } catch (NumberFormatException e) {
                return syntax;
            }
        }
    }

}
