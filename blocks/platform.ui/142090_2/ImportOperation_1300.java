	public ImportOperation(IPath containerPath, Object source,
			IImportStructureProvider provider,
			IOverwriteQuery overwriteImplementor) {
		super();
		this.destinationPath = containerPath;
		this.source = source;
		this.provider = provider;
		overwriteCallback = overwriteImplementor;
	}

	public ImportOperation(IPath containerPath, Object source,
			IImportStructureProvider provider,
			IOverwriteQuery overwriteImplementor, List filesToImport) {
		this(containerPath, source, provider, overwriteImplementor);
		setFilesToImport(filesToImport);
	}

	public ImportOperation(IPath containerPath,
			IImportStructureProvider provider,
			IOverwriteQuery overwriteImplementor, List filesToImport) {
		this(containerPath, null, provider, overwriteImplementor);
		setFilesToImport(filesToImport);
	}

