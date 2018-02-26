package net.qio.lang.utilities;

import net.qio.lang.memory.QioFunctionAllocator;
import net.qio.lang.memory.QioVariableAllocator;
import net.qio.lang.memory.Variable;
import net.qio.lang.utilities.types.Type;

public class TypeDetector {

    public static Type detect(String syntax) {
        if (syntax == null) {
            return Type.VOID;
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
                Integer.parseInt(syntax);
                return Type.INTEGER;
            } catch (NumberFormatException e) {
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
