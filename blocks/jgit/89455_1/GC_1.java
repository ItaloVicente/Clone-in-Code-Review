		final HashSet<ObjectId> ids = new HashSet<>();
		for (PackFile pack : newPacks) {
			for (PackIndex.MutableEntry entry : pack) {
				ids.add(entry.toObjectId());
			}
		}
		final ObjectReader reader = repo.newObjectReader();
		final ObjectDirectory dir = repo.getObjectDatabase();
		final ObjectDirectoryInserter inserter = dir.newInserter();
		final boolean shouldLoosen = getExpireDate() < Long.MAX_VALUE;

