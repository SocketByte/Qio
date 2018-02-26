package net.qio.lang.utilities;

import lombok.experimental.UtilityClass;
import net.qio.lang.exceptions.SyntaxException;

@UtilityClass
public class StringUtilities {

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
