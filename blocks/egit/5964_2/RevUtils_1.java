	public static boolean isContainedInAnyRef(Repository repo,
			ObjectId commitId, Collection<Ref> refs) throws IOException {
		for (Ref ref : refs)
			if (commitId.equals(ref.getObjectId()))
				return true;

		RevWalk walk = new RevWalk(repo);
		try {
			RevCommit commit = walk.parseCommit(commitId);
			for (Ref ref : refs) {
				RevCommit refCommit = walk.parseCommit(ref.getObjectId());
				boolean contained = walk.isMergedInto(commit, refCommit);
				if (contained)
					return true;
			}
		} finally {
			walk.dispose();
		}
		return false;
	}

