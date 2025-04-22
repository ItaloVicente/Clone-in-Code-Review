	protected void deleteTrashFile(final String name) throws IOException {
		File path = new File(db.getWorkTree()
		if (!path.delete())
			throw new IOException("Could not delete file " + path.getPath());
	}

