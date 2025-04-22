		List<ObjectId> remoteObjects = new ArrayList<ObjectId>(getRefs().size());
		List<ObjectId> newObjects = new ArrayList<ObjectId>(refUpdates.size());

		final long start;
		final PackWriter writer = new PackWriter(local);
		try {
