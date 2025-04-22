    /**
     * Constructs a RetargetAction with the given action id, text and style.
     *
     * @param actionID the retargetable action id
     * @param text the action's text, or <code>null</code> if there is no text
     * @param style one of <code>AS_PUSH_BUTTON</code>, <code>AS_CHECK_BOX</code>,
     * 		<code>AS_DROP_DOWN_MENU</code>, <code>AS_RADIO_BUTTON</code>, and
     * 		<code>AS_UNSPECIFIED</code>
     * @since 3.0
     */
    public RetargetAction(String actionID, String text, int style) {
        super(text, style);
        setId(actionID);
        setEnabled(false);
        super.setHelpListener(e -> {
		    HelpListener listener = null;
		    if (handler != null) {
		        listener = handler.getHelpListener();
		        if (listener == null) {
