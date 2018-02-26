package net.qio.lang.memory;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class QioVariableHolder {
    @Getter
    private static final List<String> variableList = new LinkedList<>();
}
