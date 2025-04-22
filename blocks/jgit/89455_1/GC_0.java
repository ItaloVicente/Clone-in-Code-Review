	private void loosen(ObjectDirectoryInserter inserter
			throws IOException {
		for (PackIndex.MutableEntry entry : pack) {
			final ObjectId oid = entry.toObjectId();
			if (!existing.contains(oid)) {
				existing.add(oid);
				try {
					ObjectLoader loader = reader.open(oid);
					inserter.insert(loader.getType()
							loader.getSize()
							loader.openStream()
							true);
				} catch (MissingObjectException e) {
					throw new IOException(e);
				}
			}
		}
	}

