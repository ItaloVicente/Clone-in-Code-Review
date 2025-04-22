	/**
	 * Automated test that checks all the labels and buttons of a dialog to make
	 * sure there is enough room to display all the text. Any text that wraps is
	 * only approximated and is currently not accurate.
	 *
	 * @param dialog
	 *            the test dialog to be verified.
	 * @param assertion
	 *            this is the test case object, assertions will be executed on this
	 *            object.
	 * @deprecated The <code>junit.framework.Assert</code> parameter is not used at
	 *             all. Use {@link #assertDialogTexts(Dialog)
	 *             assertDialogTexts(Dialog)} method.
	 */
	@Deprecated
	public static void assertDialogTexts(Dialog dialog, Assert assertion) {
		assertDialogTexts(dialog);
	}

