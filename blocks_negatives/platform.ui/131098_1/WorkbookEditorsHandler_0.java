	/** Return the editor input path relative to the workspace root */
	private String getEditorInputPath(EditorReference editorReference) {
		try {
			IEditorInput input = editorReference.getEditorInput();
			return Adapters.adapt(input, IPathEditorInput.class).getPath().makeRelativeTo(Platform.getLocation())
					.toOSString();
		} catch (PartInitException e) {
			WorkbenchPlugin.log(getClass(), "getWorkbenchPartReferenceText", e); //$NON-NLS-1$
		}
	}

