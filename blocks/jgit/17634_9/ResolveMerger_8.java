	private static File getWorkTreeFile(Repository repository
		File workTree = repository.getWorkTree();
		if (workTree == null)
			throw new UnsupportedOperationException();

		File workTreeFile = new File(workTree
		File parentFolder = workTreeFile.getParentFile();
		if (!parentFolder.exists())
			parentFolder.mkdirs();

		return workTreeFile;
	}

	private static MergeDriver findMergeDriver(Repository repository
			String filePath
			CanonicalTreeParser theirs
			throws IOException {
		MergeDriver driver = MergeDriverRegistry.findMergeDriver(filePath);
		if (driver == null) {
			driver = new TextMergeDriver();
