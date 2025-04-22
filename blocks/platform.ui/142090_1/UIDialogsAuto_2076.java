		DialogCheck.assertDialogTexts(dialog);
	}

	public void testCopyMoveProject() {
		IProject dummyProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject("DummyProject");
		Dialog dialog = new ProjectLocationSelectionDialog(getShell(),
				dummyProject);
		DialogCheck.assertDialogTexts(dialog);
	}

	public void testCopyMoveResource() {
		Dialog dialog = new ContainerSelectionDialog(getShell(), null, true,
				"Copy Resources");
		DialogCheck.assertDialogTexts(dialog);
	}

	public void testEditActionSetsDialog() {
	}

	public void testEditorSelection() {
		Dialog dialog = new EditorSelectionDialog(getShell());
		DialogCheck.assertDialogTexts(dialog);
	}

	public void testNavigatorFilter() {
