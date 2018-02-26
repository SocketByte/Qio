package net.qio.lang.utilities;

import lombok.experimental.UtilityClass;

@UtilityClass
public class QioObjectIdentifier {

    public static int getHashCode(Object object) {
        return System.identityHashCode(object);
    }

}
