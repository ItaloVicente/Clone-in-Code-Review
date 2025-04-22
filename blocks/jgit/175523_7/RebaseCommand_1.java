		try (RevWalk r = new RevWalk(repo)) {
			r.sort(RevSort.TOPO_KEEP_BRANCH_TOGETHER
			r.sort(RevSort.COMMIT_TIME_DESC
			r.markUninteresting(r.lookupCommit(upstreamCommit));
			r.markStart(r.lookupCommit(headCommit));
			Iterator<RevCommit> commitsToUse = r.iterator();
			while (commitsToUse != null && commitsToUse.hasNext()) {
				RevCommit commit = commitsToUse.next();
				if (preserveMerges || commit.getParentCount() == 1)
					cherryPickList.add(commit);
			}
