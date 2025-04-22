	IProgressMonitor monitor;

	Object root;

	IImportStructureProvider provider;

	String desiredExtensions[];

	FileSystemElement result;

	public SelectFilesOperation(Object rootObject,
			IImportStructureProvider structureProvider) {
		super();
		root = rootObject;
		provider = structureProvider;
	}

	protected FileSystemElement createElement(FileSystemElement parent,
			Object fileSystemObject) throws InterruptedException {
		ModalContext.checkCanceled(monitor);
		boolean isContainer = provider.isFolder(fileSystemObject);
		String elementLabel = parent == null ? provider
				.getFullPath(fileSystemObject) : provider
				.getLabel(fileSystemObject);

		if (!isContainer && !hasDesiredExtension(elementLabel)) {
