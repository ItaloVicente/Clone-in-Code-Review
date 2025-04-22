	private static File updateWorkTree(Repository repository
			File mergedContent) throws IOException {
		File workTree = repository.getWorkTree();
		if (workTree == null)
			throw new UnsupportedOperationException();

		File workTreeFile = new File(workTree
		File parentFolder = workTreeFile.getParentFile();
		if (!parentFolder.exists())
			parentFolder.mkdirs();
		FS.DETECTED.copyFile(mergedContent
		return workTreeFile;
	}

	private static MergeDriver findMergeDriver(Repository repository
			String filePath
			CanonicalTreeParser theirs) throws IOException {
		MergeDriver driver = MergeDriverRegistry.findMergeDriver(filePath);
		if (driver == null) {
			final boolean isBinary;
			if (ours != null
					&& !ours.getEntryObjectId().equals(ObjectId.zeroId()))
				isBinary = RawText.isBinary(repository.open(
						ours.getEntryObjectId()
						Constants.OBJ_BLOB).getCachedBytes());
			else if (theirs != null
					&& !theirs.getEntryObjectId().equals(ObjectId.zeroId()))
				isBinary = RawText.isBinary(repository.open(
						theirs.getEntryObjectId()
						Constants.OBJ_BLOB).getCachedBytes());
			else
				isBinary = false;

			if (isBinary)
				driver = new BinaryMergeDriver();
			else
				driver = new TextMergeDriver();
