		boolean exists = hasWithoutRefresh(objectId);
		if (trustFolderStat || exists) {
			return exists;
		}
		try (InputStream stream = Files.newInputStream(directory.toPath())) {
		} catch (IOException e) {
			return false;
		}
		return hasWithoutRefresh(objectId);
	}

	private boolean hasWithoutRefresh(AnyObjectId objectId) {
