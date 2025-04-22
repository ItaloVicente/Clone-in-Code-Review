	/**
	 * Asserts that a given dialog is not null and that it passes certain visual
	 * tests. These tests will be verified manually by the tester using an input
	 * dialog. Use this assert method to verify a dialog's sizing, initial focus, or
	 * accessibility. To ensure that both the input dialog and the test dialog are
	 * accessible by the tester, the getShell() method should be used when creating
	 * the test dialog.
	 *
	 * Example usage:
	 * <code>Dialog dialog = new AboutDialog( DialogCheck.getShell() );
	 * DialogCheck.assertDialog(dialog, this);</code>
	 *
	 * @param dialog
	 *            the test dialog to be verified.
	 * @param assertion
	 *            this is the test case object, assertions will be executed on this
	 *            object.
	 *
	 * @deprecated The <code>junit.framework.Assert</code> parameter is not used at
	 *             all. Use {@link #assertDialog(Dialog) assertDialog(Dialog)}
	 *             method.
	 */
	@Deprecated
	public static void assertDialog(Dialog dialog, Assert assertion) {
		assertDialog(dialog);
	}

