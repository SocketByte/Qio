package net.qio.lang.utilities.types;

import lombok.Getter;

public enum Condition {

    MORE(">"),
    LESS("<"),
    MORE_OR_EQUAL(">="),
    LESS_OR_EQUAL("<="),
    EQUAL("=="),
    NOT_EQUAL("!=");

    @Getter
    private String regex;

    Condition(String regex) {
        this.regex = regex;
    }

    public static String[] regexes() {
        String[] regexes = new String[values().length];
        for (int i = 0; i < regexes.length; i++)
            regexes[i] = values()[i].getRegex();
        return regexes;
    }

    public static Condition conditionOf(String regex) {
        for (Condition condition : values())
            if (condition.getRegex().equals(regex))
                return condition;
        return null;
    }
}
