package net.qio.lang.memory;

import lombok.Data;

import java.util.Objects;

@Data
public class Variable<T> extends QioReference {

    private String name;
    private T value;
    private Class<T> type;

    public boolean isReference() {
        return value instanceof Variable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable<?> variable = (Variable<?>) o;
        return Objects.equals(name, variable.name) &&
                Objects.equals(value, variable.value);
    }

    @Override
    public int hashCode() {
        return getHashCode();
    }
}
