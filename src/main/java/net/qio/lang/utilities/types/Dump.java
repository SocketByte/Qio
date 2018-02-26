package net.qio.lang.utilities.types;

import lombok.Getter;

public enum Dump {

    SPACE(' '),
    DOUBLE_QUOTE('"'),
    QUOTE('\''),
    APOSTROPHE('`');

    @Getter
    private char dumpedCharacter;

    Dump(char dumpedCharacter) {
        this.dumpedCharacter = dumpedCharacter;
    }
}
