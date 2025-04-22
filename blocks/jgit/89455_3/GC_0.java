	private void loosen(ObjectDirectoryInserter inserter
			throws IOException {
		for (PackIndex.MutableEntry entry : pack) {
			ObjectId oid = entry.toObjectId();
			if (existing.contains(oid)) {
				continue;
			}
			existing.add(oid);
			ObjectLoader loader = reader.open(oid);
			inserter.insert(loader.getType()
					loader.getSize()
					loader.openStream()
		}
	}

