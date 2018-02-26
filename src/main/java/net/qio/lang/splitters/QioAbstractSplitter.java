package net.qio.lang.splitters;

import net.qio.lang.exceptions.SyntaxException;
import net.qio.lang.utilities.KeyPair;

public abstract class QioAbstractSplitter<A, B> implements QioSplitter<A, B> {

    private final String source;
    private A firstPart;
    private B secondPart;

    public QioAbstractSplitter(String source) {
        this.source = source;
        apply();
    }

    @Override
    public void apply() {
        try {
            new QioSplitAttempt<>(this);
        } catch (SyntaxException e) {
            e.printStackTrace();
        }
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
