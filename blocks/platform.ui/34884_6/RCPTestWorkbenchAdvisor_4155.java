package org.eclipse.ui.tests.harness.util;

import org.eclipse.jface.util.Util;

public class PlatformUtil {

    public static boolean onMac() {
        return Util.isMac();
    }
}
