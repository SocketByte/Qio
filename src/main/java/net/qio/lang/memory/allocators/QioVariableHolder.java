package net.qio.lang.memory.allocators;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class QioVariableHolder {
    @Getter
    private static final List<String> variableList = new LinkedList<>();
    @Getter
    private static final Map<String, List<String>> variableListLoop = new LinkedHashMap<>();
}
