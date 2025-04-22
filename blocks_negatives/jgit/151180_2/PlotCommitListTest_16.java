		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.lookupCommit(a));
		pw.markStart(pw.lookupCommit(b2));
		PlotCommitList<PlotLane> pcl = new PlotCommitList<>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);

		CommitListAssert test = new CommitListAssert(pcl);
		test.commit(b2).lanePos(0);
		test.commit(b1).lanePos(0);
		test.commit(a).lanePos(0);
		test.noMoreCommits();
