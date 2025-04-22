	 * Returns a boolean indicating whether the dialog should be
	 * considered resizable when the shell style is initially
	 * set.
	 *
	 * This method is used to ensure that all style
	 * bits appropriate for resizable dialogs are added to the
	 * shell style.  Individual dialogs may always set the shell
	 * style to ensure that a dialog is resizable, but using this
	 * method ensures that resizable dialogs will be created with
	 * the same set of style bits.
	 *
	 * Style bits will never be removed based on the return value
	 * of this method.  For example, if a dialog returns
	 * <code>false</code>, but also sets a style bit for a
	 * SWT.RESIZE border, the style bit will be honored.
