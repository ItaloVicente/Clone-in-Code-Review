	private Tree mapTree(String rev) throws IOException {
		if (rev.startsWith(Constants.R_TAGS)) {
			Tag tag = repo.mapTag(rev);
			if (tag != null) {
				Commit commit = repo.mapCommit(tag.getObjId());
				if (commit != null)
					return commit.getTree();
			}
			return null;
		} else
			return repo.mapTree(rev);
	}

	private ObjectId getObjecId(String rev) throws IOException {
		if (rev.startsWith(Constants.R_TAGS)) {
			Tag mapTag = repo.mapTag(rev);
			if (mapTag != null)
				return mapTag.getObjId();
		} else {
			Commit mapCommit = repo.mapCommit(rev);
			if (mapCommit != null)
				return mapCommit.getCommitId();
		}
		return null;
	}

