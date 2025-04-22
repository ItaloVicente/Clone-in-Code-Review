	private File writeTrashFile(final String name, final String data)
			throws IOException {
		File path = new File(db.getWorkTree(), name);
		write(path, data);
		return path;
	}

	private void deleteTrashFile(final String name) throws IOException {
		File path = new File(db.getWorkTree(), name);
		FileUtils.delete(path);
	}

