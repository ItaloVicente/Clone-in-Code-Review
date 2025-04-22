	@Nullable
	private static RevCommit objectIdToRevCommit(RevWalk walk
			ObjectId objectId) {
		if (objectId == null) {
			return null;
		}
