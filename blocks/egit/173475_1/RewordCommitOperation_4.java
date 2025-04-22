		DirCache index = repository.lockDirCache();
		try (RevWalk walk = new RevWalk(repository)) {
			commit = walk.parseCommit(commit);
			if (newMessage.equals(commit.getFullMessage())) {
				return;
			}
			Ref ref = repository.exactRef(Constants.HEAD);
			if (ref == null) {
				throw new TeamException(CoreText.RewordCommitOperation_noHead);
			}
			headId = ref.getObjectId();
			if (headId == null || ObjectId.zeroId().equals(headId)) {
				throw new TeamException(CoreText.RewordCommitOperation_noHead);
			}
			Deque<RevCommit> commits = new LinkedList<>();
			walk.setRetainBody(true);
			if (!commit.getId().equals(headId)) {
				walk.sort(RevSort.TOPO);
				walk.sort(RevSort.COMMIT_TIME_DESC, true);
				walk.markStart(walk.parseCommit(headId));
				for (RevCommit p : commit.getParents()) {
					RevCommit parsed = walk.parseCommit(p);
					walk.markUninteresting(parsed);
				}
				RevCommit c;
				while ((c = walk.next()) != null) {
					if (c.getId().equals(commit.getId())) {
						break;
