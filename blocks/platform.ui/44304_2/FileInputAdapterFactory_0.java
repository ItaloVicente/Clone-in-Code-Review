	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (IFile.class.equals(adapterType)) {
			return adapterType.cast(((IFileEditorInput) adaptableObject).getFile());
		}
		if (IResource.class.equals(adapterType)) {
			return adapterType.cast(((IFileEditorInput) adaptableObject).getFile());
		}
