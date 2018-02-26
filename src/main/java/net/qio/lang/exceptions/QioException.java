package net.qio.lang.exceptions;

import net.qio.lang.QioInterpreter;

public abstract class QioException extends Exception {

    public QioException(String message) {
        super(message + " (on line " + QioInterpreter.getCurrentLine() + ")");
    }

}
