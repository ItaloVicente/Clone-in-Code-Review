	private static IEditorDescriptor getAlternateEditor() {
		Shell shell = ProgressManagerUtil.getDefaultParent();
		EditorSelectionDialog dialog = new EditorSelectionDialog(shell) {
			@Override
			protected IDialogSettings getDialogSettings() {
				IDialogSettings result = new DialogSettings("EditorSelectionDialog"); //$NON-NLS-1$
				result.put(EditorSelectionDialog.STORE_ID_INTERNAL_EXTERNAL, true);
				return result;
			}
		};
		dialog.setMessage(WorkbenchMessages.EditorManager_largeDocumentWarning);

		if (dialog.open() == Window.OK)
			return dialog.getSelectedEditor();
		return null;
	}

	boolean isLargeDocument(IEditorInput editorInput) {

		if (!checkDocumentSize)
			return false;

		if (!(editorInput instanceof IPathEditorInput))
			return false; // we know nothing about it

		try {
			IPath path = ((IPathEditorInput) editorInput).getPath();
			File file = new File(path.toOSString());
			return file.length() > maxFileSize;
		} catch (Exception e) {
			return false;
		}
	}

