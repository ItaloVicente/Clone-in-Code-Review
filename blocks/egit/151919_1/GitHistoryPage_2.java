	private RevCommit toRevCommit(RevWalk w, AnyObjectId id) {
		try {
			return w.parseCommit(id);
		} catch (IOException e) {
			return null;
		}
	}
