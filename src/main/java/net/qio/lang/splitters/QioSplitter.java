package net.qio.lang.splitters;

import net.qio.lang.utilities.types.Dump;
import net.qio.lang.utilities.KeyPair;

public interface QioSplitter<A, B> {

    KeyPair<A, B> split();
    A getFirstPart();
    B getSecondPart();
    String getSource();
    String[] getPossibleRegexes();
    Dump[] getDumpedCharacters();
    void apply();

}
