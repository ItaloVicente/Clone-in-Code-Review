		Iterator<RevCommit> commitsToUse;
		try (RevWalk r = new RevWalk(repo);) {
			r.sort(RevSort.TOPO_KEEP_BRANCH_TOGETHER
			r.sort(RevSort.COMMIT_TIME_DESC
			r.markUninteresting(r.lookupCommit(upstreamCommit));
			r.markStart(r.lookupCommit(headCommit));
			commitsToUse = r.iterator();
