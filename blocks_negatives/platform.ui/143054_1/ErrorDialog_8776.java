	 * @param parentShell
	 *            the parent shell of the dialog, or <code>null</code> if none
	 * @param title
	 *            the title to use for this dialog, or <code>null</code> to
	 *            indicate that the default title should be used
	 * @param message
	 *            the message to show in this dialog, or <code>null</code> to
	 *            indicate that the error's message should be shown as the
	 *            primary message
	 * @param status
	 *            the error to show to the user
	 * @param displayMask
	 *            the mask to use to filter the displaying of child items, as
	 *            per <code>IStatus.matches</code>
	 * @return the code of the button that was pressed that resulted in this
	 *         dialog closing. This will be <code>Dialog.OK</code> if the OK
	 *         button was pressed, or <code>Dialog.CANCEL</code> if this
	 *         dialog's close window decoration or the ESC key was used.
