		if (dialog.open() == Window.OK)
			return dialog.getSelectedEditor();
		return null;
	}

	boolean isLargeDocument(IEditorInput editorInput) {

		if (!checkDocumentSize)
			return false;

		if (!(editorInput instanceof IPathEditorInput))

		try {
			IPath path = ((IPathEditorInput) editorInput).getPath();
			File file = new File(path.toOSString());
			return file.length() > maxFileSize;
		} catch (Exception e) {
			return false;
		}
	}
