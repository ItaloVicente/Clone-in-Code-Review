		smallIdx = openPackIndex(getFileForPack34be9032());
		denseIdx = openPackIndex(getFileForPackdf2982f28());
	}

	protected PackIndex openPackIndex(File file) throws IOException {
		return PackIndex.open(file
