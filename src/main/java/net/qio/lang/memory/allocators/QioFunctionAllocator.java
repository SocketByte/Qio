package net.qio.lang.memory.allocators;

import lombok.Getter;
import net.qio.lang.memory.Function;
import net.qio.lang.memory.work.Work;

import java.util.LinkedHashMap;
import java.util.Map;

public class QioFunctionAllocator {

    @Getter
    private static final Map<String, Function> functions = new LinkedHashMap<>();

    @SuppressWarnings("unchecked")
    public static void setFunction(String name, Object callback, Work work) {
        Function function = pull(name);
        function.setWork(work);
        function.setCallback(callback);
        push(function);
    }

    public static <T> Function<T> createDummyFunction(String name) {
        Function<T> function = new Function<>(name);
        push(function);

        return function;
    }

    public static boolean containsFunction(String name) {
        return functions.containsKey(name);
    }

    public static Function pull(String name) {
        return functions.get(name);
    }

    public static Object getCallback(String name) {
        Function function = pull(name);
        function.invoke();
        return function.getCallback();
    }

    public static void invoke(String name) {
        pull(name).invoke();
    }

    public static void push(Function function) {
        functions.put(function.getName(), function);
    }

}
