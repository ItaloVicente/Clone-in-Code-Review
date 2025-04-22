
package org.eclipse.ui.internal.keys;

import java.util.Comparator;

import org.eclipse.jface.util.Util;
import org.eclipse.ui.keys.ModifierKey;

class NativeModifierKeyComparator implements Comparator {

    private final static int UNKNOWN_KEY = Integer.MAX_VALUE;

    @Override
	public int compare(Object left, Object right) {
        ModifierKey modifierKeyLeft = (ModifierKey) left;
        ModifierKey modifierKeyRight = (ModifierKey) right;
        int modifierKeyLeftRank = rank(modifierKeyLeft);
        int modifierKeyRightRank = rank(modifierKeyRight);

        if (modifierKeyLeftRank != modifierKeyRightRank) {
            return modifierKeyLeftRank - modifierKeyRightRank;
        } else {
            return modifierKeyLeft.compareTo(modifierKeyRight);
        }
    }

    private int rank(ModifierKey modifierKey) {

        if (Util.isWindows()) {
            return rankWindows(modifierKey);
        }

        if (Util.isGtk()) {
            return rankGNOME(modifierKey);
        }

        if (Util.isMac()) {
            return rankMacOSX(modifierKey);
        }

        if (Util.isMotif()) {
            return rankGNOME(modifierKey);
        }

        return UNKNOWN_KEY;
    }

    private final int rankGNOME(ModifierKey modifierKey) {
        if (ModifierKey.SHIFT.equals(modifierKey)) {
            return 0;
        }

        if (ModifierKey.CTRL.equals(modifierKey)) {
            return 1;
        }

        if (ModifierKey.ALT.equals(modifierKey)) {
            return 2;
        }

        return UNKNOWN_KEY;

    }


    private final int rankMacOSX(ModifierKey modifierKey) {
        if (ModifierKey.SHIFT.equals(modifierKey)) {
            return 0;
        }

        if (ModifierKey.CTRL.equals(modifierKey)) {
            return 1;
        }

        if (ModifierKey.ALT.equals(modifierKey)) {
            return 2;
        }

        if (ModifierKey.COMMAND.equals(modifierKey)) {
            return 3;
        }

        return UNKNOWN_KEY;
    }

    private final int rankWindows(ModifierKey modifierKey) {
        if (ModifierKey.CTRL.equals(modifierKey)) {
            return 0;
        }

        if (ModifierKey.ALT.equals(modifierKey)) {
            return 1;
        }

        if (ModifierKey.SHIFT.equals(modifierKey)) {
            return 2;
        }

        return UNKNOWN_KEY;
    }
}
