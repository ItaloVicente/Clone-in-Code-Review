		String titlePath = null;
		if (lastActiveEditor != null) {
			IEditorInput editorInput = lastActiveEditor.getEditorInput();
			if (editorInput instanceof IFileEditorInput) {
				titlePath = computeTitlePath((IFileEditorInput) editorInput);
			} else if (editorInput instanceof FileStoreEditorInput) {
				titlePath = computeTitlePath((FileStoreEditorInput) editorInput);
			}
		}
		titlePathUpdater.updateTitlePath(getWindowConfigurer().getWindow().getShell(), titlePath);
	}

	private String computeTitlePath(FileStoreEditorInput editorInput) {
		return editorInput.getURI().getPath();
	}

	private String computeTitlePath(IFileEditorInput editorInput) {
		IFile file = editorInput.getFile();
		IPath location = file.getLocation();
		if (location != null) {
			return location.toFile().toString();
		}
		return null;
	}
