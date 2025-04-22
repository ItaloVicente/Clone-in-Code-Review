	protected FileSystemElement createElement(FileSystemElement parent,
			Object fileSystemObject, int depth) throws InterruptedException {
		ModalContext.checkCanceled(monitor);
		boolean isContainer = provider.isFolder(fileSystemObject);
		String elementLabel = parent == null ? provider
				.getFullPath(fileSystemObject) : provider
				.getLabel(fileSystemObject);
