	@Test
	public void testDuplicateParents() throws Exception {
		final RevCommit m1 = commit();
		final RevCommit m2 = commit(m1);
		final RevCommit m3 = commit(m2

		final RevCommit s1 = commit(m2);
		final RevCommit s2 = commit(s1);

		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.lookupCommit(m3));
		pw.markStart(pw.lookupCommit(s2));
		PlotCommitList<PlotLane> pcl = new PlotCommitList<PlotLane>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);

		CommitListAssert test = new CommitListAssert(pcl);
		test.commit(s2).nrOfPassingLanes(0);
		test.commit(s1).nrOfPassingLanes(0);
		test.commit(m3).nrOfPassingLanes(1);
		test.commit(m2).nrOfPassingLanes(0);
		test.commit(m1).nrOfPassingLanes(0);
		test.noMoreCommits();
	}

