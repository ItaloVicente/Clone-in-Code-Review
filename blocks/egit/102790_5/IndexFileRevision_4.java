		}
		return null;
	}

	private ObjectId locateBlobObjectId(DirCache cache) {
		int firstIndex = cache.findEntry(path);
		if (firstIndex < 0)
