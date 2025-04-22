	public ZipFileStructureProvider(ZipFile sourceFile) {
		super();
		zipFile = sourceFile;
	}

	protected void addToChildren(ZipEntry parent, ZipEntry child) {
