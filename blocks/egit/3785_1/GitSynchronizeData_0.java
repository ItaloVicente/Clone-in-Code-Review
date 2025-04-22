	private RevCommit getCommit(String rev, ObjectWalk ow) throws IOException {
		if (rev.length() > 0) {
			ObjectId id = repo.resolve(rev);
			return id != null ? ow.parseCommit(id) : null;
		} else
			return null;
	}

