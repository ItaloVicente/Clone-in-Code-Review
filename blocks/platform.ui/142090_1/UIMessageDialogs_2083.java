	private static final String DUMMY_RESOURCE = "Dummy.resource";

	private static final String DUMMY_PROJECT = "DummyProject";

	private static final String DUMMY_ABSOLUTE_PATH = "C:\\Dummypath\\Dummy.resource";

	private static final String DUMMY_RELATIVE_PATH = "\\" + DUMMY_PROJECT
			+ "\\" + DUMMY_RESOURCE;

	public UIMessageDialogs(String name) {
		super(name);
	}

	private Shell getShell() {
		return DialogCheck.getShell();
	}

	private MessageDialog getInformationDialog(String title, String message) {
		return new MessageDialog(getShell(), title, null, message,
				MessageDialog.INFORMATION, 0,
				IDialogConstants.OK_LABEL);
	}

	private MessageDialog getQuestionDialog(String title, String message) {
		return new MessageDialog(getShell(), title, null, message,
				MessageDialog.QUESTION, 0,
				IDialogConstants.YES_LABEL,IDialogConstants.NO_LABEL );
	}

	private MessageDialog getWarningDialog(String title, String message) {
		return new MessageDialog(getShell(), title, null, message,
				MessageDialog.WARNING, 0,
				IDialogConstants.OK_LABEL);
	}

	public void testAbortPageFlipping() {
		Dialog dialog = getWarningDialog(JFaceResources
				.getString("AbortPageFlippingDialog.title"), JFaceResources
				.getString("AbortPageFlippingDialog.message"));
