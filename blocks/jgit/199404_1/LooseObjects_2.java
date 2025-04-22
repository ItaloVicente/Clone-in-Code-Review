		try {
			return getSizeWithoutRefresh(curs
		} catch (FileNotFoundException noFile) {
			try {
				if (trustFolderStat) {
					throw noFile;
				}
				try (InputStream stream = Files
						.newInputStream(directory.toPath())) {
				}
				return getSizeWithoutRefresh(curs
			} catch (FileNotFoundException e) {
				if (fileFor(id).exists()) {
					throw noFile;
				}
				unpackedObjectCache().remove(id);
				return -1;
			}
		}
	}

	private long getSizeWithoutRefresh(WindowCursor curs
			throws IOException {
