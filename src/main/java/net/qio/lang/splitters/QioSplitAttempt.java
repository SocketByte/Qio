package net.qio.lang.splitters;

import net.qio.lang.exceptions.SyntaxException;
import net.qio.lang.utilities.types.Dump;

public class QioSplitAttempt<A, B> {

    @SuppressWarnings("unchecked")
    public QioSplitAttempt(QioAbstractSplitter<A, B> abstractSplitter) throws SyntaxException {
        String source = abstractSplitter.getSource();
        Dump[] dumps = abstractSplitter.getDumpedCharacters();
        String[] regexes = abstractSplitter.getPossibleRegexes();

        A firstPart = null;
        B secondPart = null;

        for (String regex : regexes) {
            try {
                String[] split = source.split(regex);
                String first = split[0];
                String second = split[1];

                for (Dump dump : dumps) {
                    first = first.replace(String.valueOf(dump.getDumpedCharacter()), "");
                    if (dump != Dump.SPACE)
                        second = second.replace(String.valueOf(dump.getDumpedCharacter()), "");
                    else
                        second = second.replaceFirst(String.valueOf(dump.getDumpedCharacter()), "");
                }

                firstPart = (A) first;
                secondPart = (B) second;
            } catch (Exception ignore) {
            }
        }

        if (firstPart == null || secondPart == null)
            throw new SyntaxException("Incorrect syntax. Splitter could not recognize the syntax.");

        abstractSplitter.setFirstPart(firstPart);
        abstractSplitter.setSecondPart(secondPart);
    }

}
