package net.qio.lang.splitters;

import net.qio.lang.utilities.types.Condition;
import net.qio.lang.utilities.types.Dump;

public class ConditionSplitter extends QioAbstractSplitter<String, String> {
    public ConditionSplitter(String source, boolean action) {
        super(source, action);
    }

    @Override
    public String[] getPossibleRegexes() {
        return Condition.regexes();
    }

    @Override
    public Dump[] getDumpedCharacters() {
        return new Dump[] { };
    }
}
