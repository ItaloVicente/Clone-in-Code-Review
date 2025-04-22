	/**
	 * Convenience method to open a standard warning dialog.
	 *
	 * @param parent  the parent shell of the dialog, or <code>null</code> if none
	 * @param title   the dialog's title, or <code>null</code> if none
	 * @param message the message
	 */
    public static void openWarning(Shell parent, String title, String message) {
        open(WARNING, parent, title, message, SWT.NONE);
