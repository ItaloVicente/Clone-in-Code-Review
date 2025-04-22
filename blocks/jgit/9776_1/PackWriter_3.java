	public List<ObjectId> getObjectList() throws IOException {
		if (!cachedPacks.isEmpty())
			throw new IOException(
					JGitText.get().cachedPacksPreventsListingObjects);

		return Collections.unmodifiableList(
				(List<? extends ObjectId>) sortByName());
	}

