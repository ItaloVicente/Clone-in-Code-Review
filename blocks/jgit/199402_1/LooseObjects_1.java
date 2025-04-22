		try {
			return getObjectLoaderWithoutRefresh(curs
		} catch (FileNotFoundException e) {
			if (trustFolderStat) {
				throw e;
			}
			try (InputStream stream = Files
					.newInputStream(directory.toPath())) {
			}
			return getObjectLoaderWithoutRefresh(curs
		}
	}

	private ObjectLoader getObjectLoaderWithoutRefresh(WindowCursor curs
			File path
