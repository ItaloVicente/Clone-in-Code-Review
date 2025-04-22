	/**
	 * Loosen objects in a pack file which are not also in the newly-created
	 * pack files.
	 */
	private void loosen(ObjectDirectoryInserter inserter, ObjectReader reader, PackFile pack, HashSet<ObjectId> existing)
			throws IOException {
		for (PackIndex.MutableEntry entry : pack) {
			ObjectId oid = entry.toObjectId();
			if (existing.contains(oid)) {
				continue;
			}
			existing.add(oid);
			ObjectLoader loader = reader.open(oid);
			inserter.insert(loader.getType(),
					loader.getSize(),
					loader.openStream(),
					true /* create this object even though it's a duplicate */);
		}
	}

