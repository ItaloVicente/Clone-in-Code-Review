	public FileSystemExportOperation(IResource res, String destinationPath,
			IOverwriteQuery overwriteImplementor) {
		super();
		resource = res;
		path = new Path(destinationPath);
		overwriteCallback = overwriteImplementor;
	}

	public FileSystemExportOperation(IResource res, List resources,
			String destinationPath, IOverwriteQuery overwriteImplementor) {
		this(res, destinationPath, overwriteImplementor);
		resourcesToExport = resources;
	}

	protected int countChildrenOf(IResource parentResource)
			throws CoreException {
		if (parentResource.getType() == IResource.FILE) {
