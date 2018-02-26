package net.qio.lang.memory;

import lombok.Getter;
import net.qio.lang.utilities.QioObjectIdentifier;

public abstract class QioReference {

    @Getter
    private int hashCode;

    public QioReference() {
        this.hashCode = QioObjectIdentifier.getHashCode(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QioReference that = (QioReference) o;

        return hashCode == that.hashCode;
    }

    @Override
    public String toString() {
        return "ref@" + hashCode;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }
}
