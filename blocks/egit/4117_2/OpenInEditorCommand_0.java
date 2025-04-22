		IFile file = ResourcesPlugin.getWorkspace().getRoot()
				.getFileForLocation(path);
		if (file == null) {
			IFileStore store = EFS.getLocalFileSystem().getStore(path);
			try {
				IDE.openEditorOnFileStore(getView(event).getSite().getPage(),
						store);
			} catch (PartInitException e) {
				Activator.handleError(
						UIText.RepositoriesView_Error_WindowTitle, e, true);
			}
		} else
			try {
				IDE.openEditor(getView(event).getSite().getPage(), file);
			} catch (PartInitException e) {
				Activator.handleError(
						UIText.RepositoriesView_Error_WindowTitle, e, true);
			}
