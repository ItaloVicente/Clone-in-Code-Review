	private static List<RevCommit> objectIdsToRevCommits(RevWalk walk,
			Iterable<ObjectId> objectIds)
			throws MissingObjectException, IOException {
		List<RevCommit> result = new ArrayList<>();
		for (ObjectId objectId : objectIds) {
			try {
				result.add(walk.parseCommit(objectId));
			} catch (IncorrectObjectTypeException e) {
				continue;
			}
		}
		return result;
	}


