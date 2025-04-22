	private static boolean isMissing(RevWalk walk
			throws IOException {
		if (id.equals(ObjectId.zeroId())) {
		}
		try {
			walk.parseAny(id);
			return false;
		} catch (MissingObjectException e) {
			return true;
		}
	}

