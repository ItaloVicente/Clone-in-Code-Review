		final ArrayList<ObjectId> remoteObjects = new ArrayList<ObjectId>(
				getRefs().size());
		final ArrayList<ObjectId> newObjects = new ArrayList<ObjectId>(
				refUpdates.size());

		for (final Ref r : getRefs())
			remoteObjects.add(r.getObjectId());
		remoteObjects.addAll(additionalHaves);
		for (final RemoteRefUpdate r : refUpdates.values()) {
			if (!ObjectId.zeroId().equals(r.getNewObjectId()))
				newObjects.add(r.getNewObjectId());
		}
