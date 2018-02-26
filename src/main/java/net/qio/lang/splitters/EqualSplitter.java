package net.qio.lang.splitters;

import net.qio.lang.utilities.types.Dump;

public class EqualSplitter<A, B> extends QioAbstractSplitter<A, B> {

    public EqualSplitter(String source) {
        super(source);
    }

    @Override
    public String[] getPossibleRegexes() {
        return new String[] { "=", " = ", "= ", " =" };
    }

    @Override
    public Dump[] getDumpedCharacters() {
        return new Dump[] { Dump.SPACE, Dump.DOUBLE_QUOTE, Dump.QUOTE, Dump.APOSTROPHE };
    }
}
