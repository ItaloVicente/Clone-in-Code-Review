    /**
     * The default-default value for <code>FontData[]</code> preferences.
     */
    public static final FontData[] FONTDATA_ARRAY_DEFAULT_DEFAULT;

    /**
     * The default-default value for <code>FontData</code> preferences.
     */
    public static final FontData FONTDATA_DEFAULT_DEFAULT;
    static {
		Display display = Display.getCurrent();
		if (display == null) {
			display = Display.getDefault ();
		}

        FONTDATA_ARRAY_DEFAULT_DEFAULT = display.getSystemFont().getFontData();
        /**
         * The default-default value for <code>FontData</code> preferences.
         * This is left in for compatibility purposes. It is recommended that
         * FONTDATA_ARRAY_DEFAULT_DEFAULT is actually used.
         */
