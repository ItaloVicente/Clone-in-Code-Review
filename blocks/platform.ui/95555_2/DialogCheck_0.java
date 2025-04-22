	@Deprecated
	public static void assertDialog(Dialog dialog, Assert assertion) {
		assertNotNull(dialog);
		if (_verifyDialog.getShell() == null) {
			getShell();
		}
		if (_verifyDialog.open(dialog) == IDialogConstants.NO_ID) {
			assertTrue(_verifyDialog.getFailureText(), false);
		}
	}

