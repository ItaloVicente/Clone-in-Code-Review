		} catch (MissingObjectException notFound) {
			throw new WantNotValidException(notFound.getObjectId()
		}
	}

	private static List<RevObject> objectIdsToRevObjects(RevWalk walk
			Iterable<ObjectId> objectIds)
			throws MissingObjectException
		List<RevObject> result = new ArrayList<>();
		for (ObjectId objectId : objectIds) {
			result.add(walk.parseAny(objectId));
		}
		return result;
	}

	private static List<RevCommit> objectIdsToRevCommits(RevWalk walk
			Iterable<ObjectId> objectIds)
			throws MissingObjectException
		List<RevCommit> result = new ArrayList<>();
		for (ObjectId objectId : objectIds) {
			try {
				result.add(walk.parseCommit(objectId));
			} catch (IncorrectObjectTypeException e) {
				continue;
