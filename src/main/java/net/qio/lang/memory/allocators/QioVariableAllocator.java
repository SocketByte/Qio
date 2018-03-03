package net.qio.lang.memory.allocators;

import lombok.Getter;
import net.qio.lang.memory.Variable;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class QioVariableAllocator {

    @Getter
    private static final Map<String, Variable> variables = new LinkedHashMap<>();

    public static boolean containsVariable(String syntax) {
        return variables.containsKey(syntax);
    }

    @SuppressWarnings("unchecked")
    public static <T> Variable<T> createVariable(String name, T value) {
        Variable<T> variable = new Variable<>();
        variable.setName(name);
        variable.setValue(value);
        if (value.getClass().isAssignableFrom(Double.class)) {
            variable.setType((Class<T>) Integer.class);
        }
        else variable.setType((Class<T>) value.getClass());

        push(variable);
        return variable;
    }

    @SuppressWarnings("unchecked")
    public static <T> Variable<Variable<T>> createReference(String name, Variable<T> value) {
        Variable<Variable<T>> reference = new Variable<>();
        reference.setName(name);
        reference.setValue(value);
        reference.setType((Class<Variable<T>>) value.getClass());

        push(reference);
        return reference;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getDeepValue(Variable variable) {
        T result = null;
        try {
            Variable current = (Variable) variable.getValue();
            if (!current.isReference())
                return (T) current.getValue();

            while (true) {
                current = (Variable) current.getValue();

                if (!current.isReference())
                    return (T) current.getValue();
            }
        } catch (Exception ignored) {

        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static Object getRealValue(Variable variable) {
        return variable.isReference() ? getDeepValue(variable) : variable.getValue();
    }

    public static void push(Variable variable) {
        if (variables.containsKey(variable.getName()))
            return;

        variables.put(variable.getName(), variable);
    }

    public static Variable pull(String syntax) {
        String[] split = syntax.split(" ");
        for (String spl : split)
            if (variables.containsKey(spl))
                return variables.get(spl);
        return null;
    }

    public static <T> void set(Variable<T> variable, T value) {
        variable.setValue(value);
        variable.setType((Class<T>) value.getClass());
    }

    public static <T> void setReference(Variable<Variable<T>> referencedVariable, T value) {
        referencedVariable.getValue().setValue(value);
    }

    public static void printVariables() {
        for (Variable variable : variables.values()) {
            System.out.println(
                    variable.hashCode() + "    " +
                            variable.getName() + "    " +
                            variable.getValue() + "     " +
                            getRealValue(variable));
        }
    }
}
