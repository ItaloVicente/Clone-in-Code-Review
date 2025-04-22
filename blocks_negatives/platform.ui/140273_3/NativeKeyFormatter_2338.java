    /**
     * The key into the internationalization resource bundle for the delimiter
     * to use between keys (on the Carbon platform).
     */

    /**
     * A look-up table for the string representations of various carbon keys.
     */
    private static final HashMap CARBON_KEY_LOOK_UP = new HashMap();

    /**
     * A comparator to sort modifier keys in the order that they would be
     * displayed to a user. This comparator is platform-specific.
     */
    private static final Comparator MODIFIER_KEY_COMPARATOR = new NativeModifierKeyComparator();

    /**
     * The resource bundle used by <code>format()</code> to translate formal
     * string representations by locale.
     */
    private static final ResourceBundle RESOURCE_BUNDLE;

    /**
     * The key into the internationalization resource bundle for the delimiter
     * to use between key strokes (on the Win32 platform).
     */

    static {
        RESOURCE_BUNDLE = ResourceBundle.getBundle(NativeKeyFormatter.class
                .getName());

        CARBON_KEY_LOOK_UP.put(CharacterKey.BS.toString(), "\u232B");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(CharacterKey.CR.toString(), "\u21A9");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(CharacterKey.DEL.toString(), "\u2326");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(CharacterKey.SPACE.toString(), "\u2423");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(ModifierKey.ALT.toString(), "\u2325");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(ModifierKey.COMMAND.toString(), "\u2318");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(ModifierKey.CTRL.toString(), "\u2303");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(ModifierKey.SHIFT.toString(), "\u21E7");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(SpecialKey.ARROW_DOWN.toString(), "\u2193");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(SpecialKey.ARROW_LEFT.toString(), "\u2190");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(SpecialKey.ARROW_RIGHT.toString(), "\u2192");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(SpecialKey.ARROW_UP.toString(), "\u2191");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(SpecialKey.END.toString(), "\u2198");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(SpecialKey.NUMPAD_ENTER.toString(), "\u2324");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(SpecialKey.HOME.toString(), "\u2196");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(SpecialKey.PAGE_DOWN.toString(), "\u21DF");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(SpecialKey.PAGE_UP.toString(), "\u21DE");  //$NON-NLS-1$
    }

    /**
     * Formats an individual key into a human readable format. This uses an
     * internationalization resource bundle to look up the key. This does the
     * platform-specific formatting for Carbon.
     *
     * @param key
     *            The key to format; must not be <code>null</code>.
     * @return The key formatted as a string; should not be <code>null</code>.
     */
    @Override
