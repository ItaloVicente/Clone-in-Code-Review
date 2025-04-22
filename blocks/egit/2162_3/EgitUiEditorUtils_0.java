	public static void openEditor(File file, IWorkbenchPage page) {
		if (!file.exists())
			return;
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile[] files = root.findFilesForLocationURI(file.toURI());
		if (files.length > 0) {
			try {
				IDE.openEditor(page, files[0], OpenStrategy.activateOnOpen());
			} catch (PartInitException e) {
				Activator.handleError(UIText.EgitUiEditorUtils_openFailed, e,
						true);
			}
		} else {
			IFileStore store = EFS.getLocalFileSystem().getStore(
					new Path(file.getAbsolutePath()));
			try {
				IDE.openEditor(page, new FileStoreEditorInput(store),
						EditorsUI.DEFAULT_TEXT_EDITOR_ID);
			} catch (PartInitException e) {
				Activator.handleError(UIText.EgitUiEditorUtils_openFailed, e,
						true);
			}
		}
	}

