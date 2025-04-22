	/**
	 * Opens this message dialog, creating it first if it has not yet been created.
	 * <p>
	 * This method waits until the dialog is closed by the end user, and then it
	 * returns the dialog's return code. The dialog's return code is either the
	 * index of the button the user pressed, or {@link SWT#DEFAULT} if the dialog
	 * has been closed by other means.
	 * </p>
	 *
	 * @return the return code
	 *
	 * @see org.eclipse.jface.window.Window#open()
	 */
    @Override
	public int open() {
    	return super.open();
    }

