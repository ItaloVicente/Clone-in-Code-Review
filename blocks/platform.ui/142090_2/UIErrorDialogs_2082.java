	public UIErrorDialogs(String name) {
		super(name);
	}

	private Shell getShell() {
		return DialogCheck.getShell();
	}

	private ErrorDialog getMultiStatusErrorDialog() {

		IStatus[] childStatuses = new IStatus[2];
		childStatuses[0] = new Status(IStatus.ERROR, "org.eclipse.ui.tests",
				IStatus.ERROR, "Error message 1", new Throwable());
		childStatuses[1] = new Status(IStatus.ERROR, "org.eclipse.ui.tests",
				IStatus.ERROR, "Error message 2", new Throwable());
		MultiStatus mainStatus = new MultiStatus("org.eclipse.ui.tests",
				IStatus.ERROR, childStatuses, "Main error", new Throwable());

		return new ErrorDialog(getShell(), "Error Test", "Error message",
				mainStatus, IStatus.ERROR);
	}

	public void testErrorClipboard() {
		Dialog dialog = getMultiStatusErrorDialog();
		DialogCheck.assertDialog(dialog);
	}
