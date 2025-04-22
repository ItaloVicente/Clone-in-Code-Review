	@Override
	protected <T> T adaptEditorInput(IEditorInput editorInput,
			Class<T> adapter) {
		if (adapter == IFile.class) {
			return adapter.cast(
					ResourcesPlugin.getWorkspace().getRoot().getFile(location));
		}
		return super.adaptEditorInput(editorInput, adapter);
	}

