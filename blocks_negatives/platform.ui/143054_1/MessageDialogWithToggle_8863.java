    /**
     * Convenience method to open a standard error dialog.
     *
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
     * @return the dialog, after being closed by the user, which the client can
     *         only call <code>getReturnCode()</code> or
     *         <code>getToggleState()</code>
     */
    public static MessageDialogWithToggle openError(Shell parent, String title,
            String message, String toggleMessage, boolean toggleState,
            IPreferenceStore store, String key) {
    	return open(ERROR, parent, title, message, toggleMessage, toggleState, store, key, SWT.NONE);
    }

    /**
     * Convenience method to open a standard information dialog.
     *
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
     *
     * @return the dialog, after being closed by the user, which the client can
     *         only call <code>getReturnCode()</code> or
     *         <code>getToggleState()</code>
     */
    public static MessageDialogWithToggle openInformation(Shell parent,
            String title, String message, String toggleMessage,
            boolean toggleState, IPreferenceStore store, String key) {
    	return open(INFORMATION, parent, title, message, toggleMessage, toggleState, store, key, SWT.NONE);
    }

    /**
     * Convenience method to open a simple confirm (OK/Cancel) dialog.
     *
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
     * @return the dialog, after being closed by the user, which the client can
     *         only call <code>getReturnCode()</code> or
     *         <code>getToggleState()</code>
     */
    public static MessageDialogWithToggle openOkCancelConfirm(Shell parent,
            String title, String message, String toggleMessage,
            boolean toggleState, IPreferenceStore store, String key) {
    	return open(CONFIRM, parent, title, message, toggleMessage, toggleState, store, key, SWT.NONE);
    }

    /**
     * Convenience method to open a standard warning dialog.
     *
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
     * @return the dialog, after being closed by the user, which the client can
     *         only call <code>getReturnCode()</code> or
     *         <code>getToggleState()</code>
     */
    public static MessageDialogWithToggle openWarning(Shell parent,
            String title, String message, String toggleMessage,
            boolean toggleState, IPreferenceStore store, String key) {
    	return open(WARNING, parent, title, message, toggleMessage, toggleState, store, key, SWT.NONE);
    }

    /**
     * Convenience method to open a simple question Yes/No/Cancel dialog.
     *
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
     * @return the dialog, after being closed by the user, which the client can
     *         only call <code>getReturnCode()</code> or
     *         <code>getToggleState()</code>
     */
    public static MessageDialogWithToggle openYesNoCancelQuestion(Shell parent,
            String title, String message, String toggleMessage,
            boolean toggleState, IPreferenceStore store, String key) {
    	return open(QUESTION_WITH_CANCEL, parent, title, message, toggleMessage, toggleState, store, key, SWT.NONE);
    }

    /**
     * Convenience method to open a simple Yes/No question dialog.
     *
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
     *
     * @return the dialog, after being closed by the user, which the client can
     *         only call <code>getReturnCode()</code> or
     *         <code>getToggleState()</code>
     */
    public static MessageDialogWithToggle openYesNoQuestion(Shell parent,
            String title, String message, String toggleMessage,
            boolean toggleState, IPreferenceStore store, String key) {
    	return open(QUESTION, parent, title, message, toggleMessage, toggleState, store, key, SWT.NONE);
    }

    /**
     * The key at which the toggle state should be stored within the
     * preferences. This value may be <code>null</code>, which indicates that
     * no preference should be updated automatically. It is then the
     * responsibility of the user of this API to use the information from the
     * toggle. Note: a <code>prefStore</code> is also needed.
     */
    private String prefKey = null;

    /**
     * The preference store which will be affected by the toggle button. This
     * value may be <code>null</code>, which indicates that no preference
     * should be updated automatically. It is then the responsibility of the
     * user of this API to use the information from the toggle. Note: a
     * <code>prefKey</code> is also needed.
     */
    private IPreferenceStore prefStore = null;

    /**
     * The toggle button (widget). This value is <code>null</code> until the
     * dialog is created.
     */
    private Button toggleButton = null;

    /**
     * The message displayed to the user, with the toggle button. This is the
     * text besides the toggle. If it is <code>null</code>, this means that
     * the default text for the toggle should be used.
     */
    private String toggleMessage;

    /**
     * The initial selected state of the toggle.
     */
    private boolean toggleState;
