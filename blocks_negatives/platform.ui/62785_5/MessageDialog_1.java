     * Convenience method to open a standard error dialog.
     *
     * @param parent
     *            the parent shell of the dialog, or <code>null</code> if none
     * @param title
     *            the dialog's title, or <code>null</code> if none
     * @param message
     *            the message
     */
    public static void openError(Shell parent, String title, String message) {
        open(ERROR, parent, title, message, SWT.NONE);
