
	@Test
	public void testTwoRoots1() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit();

		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.lookupCommit(a));
		pw.markStart(pw.lookupCommit(b));
		PlotCommitList<PlotLane> pcl = new PlotCommitList<PlotLane>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);

		CommitListAssert test = new CommitListAssert(pcl);
		test.commit(b).lanePos(0);
		test.commit(a).lanePos(0);
		test.noMoreCommits();
	}

	@Test
	public void testTwoRoots2() throws Exception {
		final RevCommit a = commit();
		final RevCommit b1 = commit();
		final RevCommit b2 = commit(b1);

		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.lookupCommit(a));
		pw.markStart(pw.lookupCommit(b2));
		PlotCommitList<PlotLane> pcl = new PlotCommitList<PlotLane>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);

		CommitListAssert test = new CommitListAssert(pcl);
		test.commit(b2).lanePos(0);
		test.commit(b1).lanePos(0);
		test.commit(a).lanePos(0);
		test.noMoreCommits();
	}
