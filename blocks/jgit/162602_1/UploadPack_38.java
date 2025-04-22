	@Nullable
	private static RevObject objectIdToRevObject(RevWalk walk
			ObjectId objectId) {
		if (objectId == null) {
			return null;
		}

		try {
			return walk.parseAny(objectId);
		} catch (IOException e) {
			return null;
		}
	}

