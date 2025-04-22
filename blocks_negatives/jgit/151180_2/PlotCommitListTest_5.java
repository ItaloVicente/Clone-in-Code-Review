		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.lookupCommit(b.getId()));
		pw.markStart(pw.lookupCommit(c.getId()));
		pw.markStart(pw.lookupCommit(d.getId()));

		PlotCommitList<PlotLane> pcl = new PlotCommitList<>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);

		Set<Integer> childPositions = asSet(0, 1, 2);
		CommitListAssert test = new CommitListAssert(pcl);
		test.commit(d).lanePos(childPositions).parents(a);
		test.commit(c).lanePos(childPositions).parents(a);
		test.commit(b).lanePos(childPositions).parents(a);
		test.commit(a).lanePos(0).parents();
		test.noMoreCommits();
