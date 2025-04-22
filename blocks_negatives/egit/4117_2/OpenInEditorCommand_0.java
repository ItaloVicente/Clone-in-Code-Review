		IFileStore store = EFS.getLocalFileSystem().getStore(
				new Path(node.getObject().getAbsolutePath()));
		try {
			IDE.openEditor(getView(event).getSite().getPage(),
					new FileStoreEditorInput(store),
					EditorsUI.DEFAULT_TEXT_EDITOR_ID);
		} catch (PartInitException e) {
			Activator.handleError(UIText.RepositoriesView_Error_WindowTitle, e,
					true);
		}

