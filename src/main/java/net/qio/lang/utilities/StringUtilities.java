package net.qio.lang.utilities;

import lombok.experimental.UtilityClass;
import net.qio.lang.exceptions.SyntaxException;
import net.qio.lang.memory.Variable;
import net.qio.lang.memory.allocators.QioFunctionAllocator;
import net.qio.lang.memory.allocators.QioVariableAllocator;
import net.qio.lang.memory.allocators.QioWorkHolder;
import net.qio.lang.memory.temp.LoopTempContainer;
import net.qio.lang.memory.work.WorkLoop;
import net.qio.lang.splitters.ConditionSplitter;
import net.qio.lang.splitters.ConditionalChoiceSplitter;
import net.qio.lang.splitters.ConditionalOperatorSplitter;
import net.qio.lang.utilities.types.Condition;
import net.qio.lang.utilities.types.Type;

import java.util.Arrays;

@UtilityClass
public class StringUtilities {

    public static String parse(String string) {
        String[] split = string.split("&");
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : split) {
            String unspacedLine = line.replaceAll(" ", "");
            String replaced = line;
            for (Variable variable1 : QioVariableAllocator.getVariables().values()) {
                replaced = replaced.replaceAll("\\b" + variable1.getName() + "\\b",
                        QioVariableAllocator.getRealValue(variable1).toString());
            }
            if (replaced.contains("index")) {
                WorkLoop workLoop = QioWorkHolder.getLoopWorks().get(QioWorkHolder.getLoopIndex());
                LoopTempContainer container = workLoop.getLoopContainer();
                int loopCount = container.getCurrentLoopCount();

                replaced = replaced.replace("index", String.valueOf(loopCount));
            }
            double value = MathUtilities.eval(replaced);
            if (value != -Double.MAX_VALUE) {
                stringBuilder.append(value);
                continue;
            }

            if (QioVariableAllocator.containsVariable(unspacedLine)) {
                stringBuilder.append(QioVariableAllocator.getRealValue(QioVariableAllocator.pull(unspacedLine)));
            }
            else if (QioFunctionAllocator.containsFunction(unspacedLine)) {
                stringBuilder.append(QioFunctionAllocator.getCallback(unspacedLine));
            }
            else stringBuilder.append(removeQuotes(line));
        }
        return stringBuilder.toString();
    }

    public static String conditionPush(String string) {
        try {
            ConditionalOperatorSplitter splitter = new ConditionalOperatorSplitter(string);
            String condition = splitter.getFirstPart();
            String choice = splitter.getSecondPart();

            ConditionalChoiceSplitter choiceSplitter = new ConditionalChoiceSplitter(choice);
            String choiceTrue = choiceSplitter.getFirstPart();
            String choiceFalse = choiceSplitter.getSecondPart();

            ConditionSplitter conditionSplitter = new ConditionSplitter(condition, false);
            String regex = conditionSplitter.getRegex();
            Condition regexType = Condition.conditionOf(regex);
            String conditionPart1 = conditionSplitter.getFirstPart();
            String conditionPart2 = conditionSplitter.getSecondPart();

            Type type1 = TypeDetector.detect(conditionPart1, true);
            Type type2 = TypeDetector.detect(conditionPart2, true);

            Object realValue1 = TypeDetector.convertToValue(type1, conditionPart1);
            Object realValue2 = TypeDetector.convertToValue(type2, conditionPart2);
            double realValue1Double = 0, realValue2Double = 0;
            String realValue1String = null, realValue2String = null;

            try {
                realValue1Double = (double) realValue1;
                realValue2Double = (double) realValue2;
            } catch (ClassCastException ignore) {

            }
            try {
                realValue1String = String.valueOf(realValue1).replace(" ", "");
                realValue2String = String.valueOf(realValue2).replace(" ", "");
            } catch (ClassCastException ignore) {

            }
            switch (regexType) {
                case MORE:
                    if (realValue1Double > realValue2Double)
                        return choiceTrue;
                    else return choiceFalse;
                case LESS:
                    if (realValue1Double < realValue2Double)
                        return choiceTrue;
                    else return choiceFalse;
                case EQUAL:
                    if (realValue1String.equals(realValue2String))
                        return choiceTrue;
                    else return choiceFalse;
                case NOT_EQUAL:
                    if (!realValue1String.equals(realValue2String))
                        return choiceTrue;
                    else return choiceFalse;
                case LESS_OR_EQUAL:
                    if (realValue1Double <= realValue2Double)
                        return choiceTrue;
                    else return choiceFalse;
                case MORE_OR_EQUAL:
                    if (realValue1Double >= realValue2Double)
                        return choiceTrue;
                    else return choiceFalse;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static void condition(String string) {
        String function = null;
        function = conditionPush(string);
        QioFunctionAllocator.invoke(function);
    }

    public static int getTabs(String syntax) {
        int sum = 0;
        for (char c : syntax.toCharArray()) {
            if (c == '\t')
                sum++;
        }
        return sum;
    }

    public static String removeTabs(String syntax) {
        return syntax.replaceAll("\\s+", "");
    }

    public static String removeQuotes(String syntax) {
        return syntax
                .replaceAll("\"", "")
                .replaceAll("'", "")
                .replaceAll("`", "");
    }

    public static boolean checkTabs(int tabs) {
        if (tabs > 0) {
            try {
                throw new SyntaxException("Invalid syntax.");
            } catch (SyntaxException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

}
