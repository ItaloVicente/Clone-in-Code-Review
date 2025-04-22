	private void deleteRecursive(File fileOrFolder) throws IOException {
		File[] children = fileOrFolder.listFiles();
		if (children != null) {
			for (File child : children)
				deleteRecursive(child);
		}
		if (!fileOrFolder.delete())
			throw new IOException("Could not delete " + fileOrFolder.getPath());
	}

