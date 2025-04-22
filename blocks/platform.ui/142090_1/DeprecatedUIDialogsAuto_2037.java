	public DeprecatedUIDialogsAuto(String name) {
		super(name);
	}

	private Shell getShell() {
		return DialogCheck.getShell();
	}

	public void testSaveAll() {
		YesNoCancelListSelectionDialog dialog = new YesNoCancelListSelectionDialog(
				getShell(), new AdaptableList(),
				new WorkbenchContentProvider(),
				new WorkbenchPartLabelProvider(), WorkbenchMessages.EditorManager_saveResourcesMessage);
		dialog.setTitle(WorkbenchMessages.EditorManager_saveResourcesTitle);
		DialogCheck.assertDialogTexts(dialog);
	}
