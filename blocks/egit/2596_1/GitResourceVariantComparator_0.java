	private IFile getSynchronizedFile(IResource resource) throws CoreException {
		IFile file = ((IFile) resource);
		if (!file.isSynchronized(0))
			file.refreshLocal(0, null);

		return file;
	}

