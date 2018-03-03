package net.qio.lang.splitters;

import net.qio.lang.exceptions.SyntaxException;
import net.qio.lang.utilities.types.Dump;

public class ConditionalOperatorSplitter extends QioAbstractSplitter<String, String> {
    public ConditionalOperatorSplitter(String source) throws SyntaxException {
        super(source);
    }

    @Override
    public String[] getPossibleRegexes() {
        return new String[] { " \\? ", "\\?", " \\?", "\\? " };
    }

    @Override
    public Dump[] getDumpedCharacters() {
        return new Dump[] { Dump.APOSTROPHE, Dump.DOUBLE_QUOTE, Dump.QUOTE };
    }
}
