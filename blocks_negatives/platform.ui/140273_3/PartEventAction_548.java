    /**
     * Creates a new action with the given text and style.
     *
     * @param text the action's text, or <code>null</code> if there is no text
     * @param style one of <code>AS_PUSH_BUTTON</code>, <code>AS_CHECK_BOX</code>,
     * 		<code>AS_DROP_DOWN_MENU</code>, <code>AS_RADIO_BUTTON</code>, and
     * 		<code>AS_UNSPECIFIED</code>
     * @since 3.0
     */
    protected PartEventAction(String text, int style) {
        super(text, style);
    }
