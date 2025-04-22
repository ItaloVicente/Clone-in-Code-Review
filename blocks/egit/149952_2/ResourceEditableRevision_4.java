	@Override
	protected <T> T adaptEditorInput(IEditorInput editorInput,
			Class<T> adapter) {
		if (adapter == IResource.class || adapter == IFile.class) {
			return adapter.cast(file);
		}
		return super.adaptEditorInput(editorInput, adapter);
	}

