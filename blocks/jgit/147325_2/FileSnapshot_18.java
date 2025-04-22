	protected FileSnapshot(File file) {
		this(file
	}

	protected FileSnapshot(File file
		this.file = file;
		this.lastRead = Instant.now();
		this.fileStoreAttributeCache = useConfig
				? FS.getFileStoreAttributes(file.toPath().getParent())
				: FALLBACK_FILESTORE_ATTRIBUTES;
