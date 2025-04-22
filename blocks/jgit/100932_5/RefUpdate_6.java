	private static RevObject safeParseNew(RevWalk rw
			throws IOException {
		if (newId == null || ObjectId.zeroId().equals(newId)) {
			return null;
		}
		return rw.parseAny(newId);
	}

	private static RevObject safeParseOld(RevWalk rw
