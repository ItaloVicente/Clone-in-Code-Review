    }

    /**
     * Calculates a rank for a given modifier key.
     *
     * @param modifierKey
     *            The modifier key to rank; may be <code>null</code>.
     * @return The rank of this modifier key. This is a non-negative number
     *         where a lower number suggests a higher rank.
     */
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

        return UNKNOWN_KEY;
    }

    /**
     * Provides a ranking for the modifier key based on the modifier key
     * ordering used in the GNOME window manager.
     *
     * @param modifierKey
     *            The modifier key to rank; may be <code>null</code>.
     * @return The rank of this modifier key. This is a non-negative number
     *         where a lower number suggests a higher rank.
     */
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

    /**
     * Provides a ranking for the modifier key based on the modifier key
     * ordering used in the KDE window manager.
     *
     * @param modifierKey
     *            The modifier key to rank; may be <code>null</code>.
     * @return The rank of this modifier key. This is a non-negative number
     *         where a lower number suggests a higher rank.
     */
