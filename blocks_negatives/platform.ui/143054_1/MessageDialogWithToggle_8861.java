    /**
     * The value of the preference when the user has asked that the answer to
     * the question always be "okay" or "yes".
     */

    /**
     * The value of the preference when the user has asked that the answer to
     * the question always be "no".
     */

    /**
     * The value of the preference when the user wishes to prompted for an
     * answer every time the question is to be asked.
     */

    /**
     * Convenience method to open a simple dialog as specified by the <code>kind</code> flag,
     * with a "don't show again' toggle.
     *
     * @param kind
	 *            the kind of dialog to open, one of {@link #ERROR},
	 *            {@link #INFORMATION}, {@link #QUESTION}, {@link #WARNING},
	 *            {@link #CONFIRM}, or {#QUESTION_WITH_CANCEL}.
     * @param parent
     *            the parent shell of the dialog, or <code>null</code> if none
     * @param title
     *            the dialog's title, or <code>null</code> if none
     * @param message
     *            the message
     * @param toggleMessage
     *            the message for the toggle control, or <code>null</code> for
     *            the default message
     * @param toggleState
     *            the initial state for the toggle
     * @param store
     *            the IPreference store in which the user's preference should be
     *            persisted; <code>null</code> if you don't want it persisted
     *            automatically.
     * @param key
     *            the key to use when persisting the user's preference;
     *            <code>null</code> if you don't want it persisted.
	 * @param style
	 *            {@link SWT#NONE} for a default dialog, or {@link SWT#SHEET} for
	 *            a dialog with sheet behavior
     * @return the dialog, after being closed by the user, which the client can
     *         only call <code>getReturnCode()</code> or
     *         <code>getToggleState()</code>
     * @since 3.5
     */
    public static MessageDialogWithToggle open(int kind, Shell parent, String title,
            String message, String toggleMessage, boolean toggleState,
            IPreferenceStore store, String key, int style) {
        MessageDialogWithToggle dialog = new MessageDialogWithToggle(parent,
                title, null, // accept the default window icon
                message, kind, getButtonLabels(kind), 0,
                toggleMessage, toggleState);
        style &= SWT.SHEET;
        dialog.setShellStyle(dialog.getShellStyle() | style);
        dialog.prefStore = store;
        dialog.prefKey = key;
        dialog.open();
        return dialog;
    }
