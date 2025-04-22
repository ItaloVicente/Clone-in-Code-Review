		boolean exists = hasWithoutRefresh(objectId);
		if (trustFolderStat) {
			return exists;
		}
		if (!exists) {
			try (InputStream stream = Files.newInputStream(directory.toPath())) {
			} catch (IOException e) {
				return false;
			}
			return hasWithoutRefresh(objectId);
		}
		return true;
	}

	private boolean hasWithoutRefresh(AnyObjectId objectId) {
