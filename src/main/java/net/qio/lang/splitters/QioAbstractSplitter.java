package net.qio.lang.splitters;

import net.qio.lang.exceptions.SyntaxException;
import net.qio.lang.utilities.KeyPair;

public abstract class QioAbstractSplitter<A, B> implements QioSplitter<A, B> {

    private final String source;
    private A firstPart;
    private B secondPart;
    private String regex;

    public QioAbstractSplitter(String source) throws SyntaxException {
        this.source = source;
        apply();
    }

    public QioAbstractSplitter(String source, boolean noAction) {
        this.source = source;
        if (!noAction) {
            try {
                apply();
            } catch (SyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void apply() throws SyntaxException {
        new QioSplitAttempt<>(this);
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public void setFirstPart(A firstPart) {
        this.firstPart = firstPart;
    }

    public void setSecondPart(B secondPart) {
        this.secondPart = secondPart;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public KeyPair<A, B> split() {
        return new KeyPair<>(firstPart, secondPart);
    }

    @Override
    public A getFirstPart() {
        return firstPart;
    }

    @Override
    public B getSecondPart() {
        return secondPart;
    }
}
