	public WelcomeEditor() {
		super();
		setPartName(IDEWorkbenchMessages.WelcomeEditor_title);
		copyAction = new WelcomeEditorCopyAction(this);
		copyAction.setEnabled(false);
	}
