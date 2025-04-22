	private Duration fsTimestampResolution;

	private final Object fileKey;

	protected FileSnapshot(File path) {
		this.lastRead = System.currentTimeMillis();
		this.fsTimestampResolution = FS
				.getFsTimerResolution(path.toPath().getParent());
		BasicFileAttributes fileAttributes = null;
		try {
			fileAttributes = FS.DETECTED.fileAttributes(path);
		} catch (IOException e) {
			this.lastModified = path.lastModified();
			this.size = path.length();
			this.fileKey = MISSING_FILEKEY;
			return;
		}
		this.lastModified = fileAttributes.lastModifiedTime().toMillis();
		this.size = fileAttributes.size();
		this.fileKey = getFileKey(fileAttributes);
	}

	private boolean sizeChanged;

	private boolean fileKeyChanged;

	private boolean lastModifiedChanged;

	private boolean wasRacyClean;

	private FileSnapshot(long read
			@NonNull Duration fsTimestampResolution
