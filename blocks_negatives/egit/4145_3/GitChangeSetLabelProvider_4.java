		RevCommit remoteCommit = commit.getBaseCommit();
		ObjectReader reader = commit.getRepository().newObjectReader();
		ObjectId commitId = remoteCommit.getId();
		AbbreviatedObjectId shortId;
		try {
			shortId = reader.abbreviate(commitId, 6);
		} catch (IOException e) {
			shortId = AbbreviatedObjectId.fromObjectId(ObjectId.zeroId());
			Activator.logError(e.getMessage(), e);
		} finally {
			reader.release();
		}
		return shortId.name();
