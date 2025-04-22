			} catch (PartInitException e) {
				Activator.handleError(UIText.EgitUiEditorUtils_openFailed, e,
						true);
			}
		} else {
			IFileStore store = EFS.getLocalFileSystem().getStore(
					new Path(file.getAbsolutePath()));
			try {
				return IDE.openEditor(page, new FileStoreEditorInput(store),
						EditorsUI.DEFAULT_TEXT_EDITOR_ID);
			} catch (PartInitException e) {
				Activator.handleError(UIText.EgitUiEditorUtils_openFailed, e,
						true);
