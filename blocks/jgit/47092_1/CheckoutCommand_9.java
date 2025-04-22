			RevCommit headCommit = null;
			RevCommit newCommit = null;
			try (RevWalk revWalk = new RevWalk(repo)) {
				AnyObjectId headId = headRef.getObjectId();
				headCommit = headId == null ? null
						: revWalk.parseCommit(headId);
				newCommit = revWalk.parseCommit(branch);
			}
