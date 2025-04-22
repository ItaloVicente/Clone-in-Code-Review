		HashSet<ObjectId> ids = new HashSet<>();
		for (PackFile pack : newPacks) {
			for (PackIndex.MutableEntry entry : pack) {
				ids.add(entry.toObjectId());
			}
		}
		ObjectReader reader = repo.newObjectReader();
		ObjectDirectory dir = repo.getObjectDatabase();
		ObjectDirectoryInserter inserter = dir.newInserter();
		boolean shouldLoosen = getExpireDate() < Long.MAX_VALUE;

