package net.qio.lang.memory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Function<T> extends QioReference {

    private String name;
    private T callback;
    private FunctionWork work;

    public Function(String name, T callback, FunctionWork work) {
        this.name = name;
        this.callback = callback;
        this.work = work;
    }

    public Function(String name) {
        this.name = name;
    }

    public void invoke() {
        work.execute();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Function<?> function = (Function<?>) o;

        return (name != null ? name.equals(function.name) : function.name == null)
                && (callback != null ? callback.equals(function.callback) : function.callback == null)
                && (work != null ? work.equals(function.work) : function.work == null);
    }

    @Override
    public int hashCode() {
        return getHashCode();
    }
}
