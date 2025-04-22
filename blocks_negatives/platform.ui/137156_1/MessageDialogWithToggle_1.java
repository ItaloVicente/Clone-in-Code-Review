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
	 *            {@link SWT#NONE} for a default dialog, or {@link SWT#SHEET}
	 *            for a dialog with sheet behavior
	 * @param buttonLabelToIdMap
	 *            map with button labels and ids to define custom labels and
	 *            their corresponding ids
	 * @return the dialog, after being closed by the user, which the client can
	 *         only call <code>getReturnCode()</code> or
	 *         <code>getToggleState()</code>
