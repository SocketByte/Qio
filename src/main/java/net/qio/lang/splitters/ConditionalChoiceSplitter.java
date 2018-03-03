package net.qio.lang.splitters;

import net.qio.lang.exceptions.SyntaxException;
import net.qio.lang.utilities.types.Dump;

public class ConditionalChoiceSplitter extends QioAbstractSplitter<String, String> {

    public ConditionalChoiceSplitter(String source) throws SyntaxException {
        super(source);
    }

    @Override
    public String[] getPossibleRegexes() {
        return new String[] { "%", " %", "% ", " % " };
    }

    @Override
    public Dump[] getDumpedCharacters() {
        return new Dump[] { Dump.DOUBLE_QUOTE, Dump.APOSTROPHE, Dump.QUOTE };
    }
}
