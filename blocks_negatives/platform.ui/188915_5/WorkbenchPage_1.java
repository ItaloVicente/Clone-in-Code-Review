	private static IEditorDescriptor getAlternateEditor() {
		Shell shell = ProgressManagerUtil.getDefaultParent();
		EditorSelectionDialog dialog = new EditorSelectionDialog(shell) {
			@Override
			protected IDialogSettings getDialogSettings() {
				result.put(EditorSelectionDialog.STORE_ID_INTERNAL_EXTERNAL, true);
				return result;
			}
		};
		dialog.setMessage(WorkbenchMessages.EditorManager_largeDocumentWarning);

		if (dialog.open() == Window.OK)
			return dialog.getSelectedEditor();
		return null;
	}
