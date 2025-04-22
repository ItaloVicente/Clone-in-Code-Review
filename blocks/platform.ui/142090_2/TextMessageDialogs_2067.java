	public TextMessageDialogs(String name) {
		super(name);
	}

	private Shell getShell() {
		return DialogCheck.getShell();
	}

	private String getEditorString(String id) {
		ResourceBundle bundle = ResourceBundle
				.getBundle("org.eclipse.ui.texteditor.EditorMessages");
		assertNotNull("EditorMessages", bundle);
		String string = bundle.getString(id);
		assertNotNull(id, string);
		return string;
	}

	private MessageDialog getConfirmDialog(String title, String message) {
		return new MessageDialog(getShell(), title, null, message,
