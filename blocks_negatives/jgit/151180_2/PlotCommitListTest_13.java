		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.lookupCommit(a4));
		pw.markStart(pw.lookupCommit(b2));
		pw.markStart(pw.lookupCommit(c));
		PlotCommitList<PlotLane> pcl = new PlotCommitList<>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);

		Set<Integer> positions = asSet(0, 1, 2);
		CommitListAssert test = new CommitListAssert(pcl);
		int posB = test.commit(b2).lanePos(positions).getLanePos();
		int posA = test.commit(a4).lanePos(positions).getLanePos();
		test.commit(a3).lanePos(posA);
		test.commit(c).lanePos(positions);
		test.commit(a2).lanePos(posA);
		test.commit(a1).lanePos(posA);
		test.noMoreCommits();
