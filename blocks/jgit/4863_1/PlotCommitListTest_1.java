	@Test
	public void testMerged2() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(a);
		final RevCommit d = commit(a);
		final RevCommit e = commit(c);

		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.lookupCommit(e.getId()));
		pw.markStart(pw.lookupCommit(b.getId()));
		pw.markStart(pw.lookupCommit(d.getId()));

		PlotCommitList<PlotLane> pcl = new PlotCommitList<PlotLane>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);

		CommitListAssert test = new CommitListAssert(pcl);
		test.commit(e).lanePos(0).parents(c);
		test.commit(d).lanePos(1).parents(a);
		test.commit(c).lanePos(0).parents(a);
		test.commit(b).lanePos(0).parents(a);
		test.commit(a).lanePos(1).parents();
		test.noMoreCommits();
	}

