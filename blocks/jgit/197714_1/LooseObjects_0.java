		if (!hasWithoutRefresh(objectId)) {
			try (InputStream stream = Files.newInputStream(directory.toPath())) {
			} catch (IOException e) {
				return false;
			}
			return hasWithoutRefresh(objectId);
		}
		return true;
	}

	private boolean hasWithoutRefresh(AnyObjectId objectId) {
