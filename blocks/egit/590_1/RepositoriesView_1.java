	private void openFile(File file) {
		IFileStore store = EFS.getLocalFileSystem().getStore(
				new Path(file.getAbsolutePath()));
		try {
			IDE.openEditor(getSite().getPage(),
					new FileStoreEditorInput(store),
					EditorsUI.DEFAULT_TEXT_EDITOR_ID);
		} catch (PartInitException e) {
			MessageDialog.openError(getSite().getShell(),
					UIText.RepositoriesView_Error_WindowTitle, e.getMessage());
			Activator.getDefault().getLog().log(
					Activator.error("Failed to open editor on external file", e)); //$NON-NLS-1$
		}
	}

