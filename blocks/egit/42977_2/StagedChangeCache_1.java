			RevCommit headCommit = null;
			if (headId != null) {
				try (RevWalk rw = new RevWalk(repo)) {
					headCommit = rw.parseCommit(headId);
				}
			}
