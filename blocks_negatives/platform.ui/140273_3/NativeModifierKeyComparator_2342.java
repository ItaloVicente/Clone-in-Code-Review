    /**
     * Provides a ranking for the modifier key based on the modifier key
     * ordering used in the MacOS X operating system.
     *
     * @param modifierKey
     *            The modifier key to rank; may be <code>null</code>.
     * @return The rank of this modifier key. This is a non-negative number
     *         where a lower number suggests a higher rank.
     */
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

    /**
     * Provides a ranking for the modifier key based on the modifier key
     * ordering used in the Windows operating system.
     *
     * @param modifierKey
     *            The modifier key to rank; may be <code>null</code>.
     * @return The rank of this modifier key. This is a non-negative number
     *         where a lower number suggests a higher rank.
     */
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
