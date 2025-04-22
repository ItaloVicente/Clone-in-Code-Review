		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.lookupCommit(a3));
		pw.markStart(pw.lookupCommit(b1));
		PlotCommitList<PlotLane> pcl = new PlotCommitList<>();
		pcl.source(pw);

		Set<Integer> positions = asSet(0, 1);
		CommitListAssert test = new CommitListAssert(pcl);
		PlotLane laneB = test.commit(b1).lanePos(positions).current.getLane();
		int posA = test.commit(a3).lanePos(positions).getLanePos();
		test.commit(a2).lanePos(posA);
		assertArrayEquals(
				"Although the parent of b1, a1, is not processed yet, the b lane should still be drawn",
				new PlotLane[] { laneB }, test.current.passingLanes);
		test.noMoreCommits();
