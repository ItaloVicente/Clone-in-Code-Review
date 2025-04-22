        }

        return accelerator;
    }

    private static final IKeyFormatter NATIVE_FORMATTER = new NativeKeyFormatter();

    /**
     * Provides an instance of <code>IKeyFormatter</code> appropriate for the
     * current instance.
     *
     * @return an instance of <code>IKeyFormatter</code> appropriate for the
     *         current instance; never <code>null</code>.
     */
    public static IKeyFormatter getKeyFormatterForPlatform() {
        return NATIVE_FORMATTER;
    }

    /**
     * Makes sure that a fully-modified character is converted to the normal
     * form. This means that "Ctrl+" key strokes must reverse the modification
     * caused by control-escaping. Also, all lower case letters are converted
     * to uppercase.
     *
     * @param event
     *            The event from which the fully-modified character should be
     *            pulled.
     * @return The modified character, uppercase and without control-escaping.
     */
    private static char topKey(Event event) {
        char character = event.character;
        boolean ctrlDown = (event.stateMask & SWT.CTRL) != 0;

        if (ctrlDown && event.character != event.keyCode
                && event.character < 0x20) {
