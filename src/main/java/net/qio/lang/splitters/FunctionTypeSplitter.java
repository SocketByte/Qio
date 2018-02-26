package net.qio.lang.splitters;

import net.qio.lang.utilities.types.Dump;

public class FunctionTypeSplitter extends QioAbstractSplitter<String, String> {

    public FunctionTypeSplitter(String source) {
        super(source);
    }

    @Override
    public String[] getPossibleRegexes() {
        return new String[] { ": ", " :", ":", " : " };
    }

    @Override
    public Dump[] getDumpedCharacters() {
        return new Dump[] { Dump.SPACE };
    }
}
