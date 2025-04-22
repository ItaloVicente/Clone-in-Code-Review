	private ResourceUtil() {
	}

	public static IFile getFile(IEditorInput editorInput) {
		IFile file = Adapters.adapt(editorInput, IFile.class);
		if (file != null) {
			return file;
		}
		IResource resource = Adapters.adapt(editorInput, IResource.class);
		if (resource instanceof IFile) {
			return (IFile) resource;
		}
		if (editorInput instanceof IStorageEditorInput) {
			try {
				IStorage storage = ((IStorageEditorInput) editorInput).getStorage();
				if (storage != null) {
					file = Adapters.adapt(storage, IFile.class);
				}
			} catch (CoreException e) {
			}
		}
		return file;
	}

	public static IResource getResource(IEditorInput editorInput) {
