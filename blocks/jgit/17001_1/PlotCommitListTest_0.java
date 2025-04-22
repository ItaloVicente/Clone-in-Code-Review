	@Test
	public void testBug368927() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);
		final RevCommit d = commit(b);
		final RevCommit e = commit(c);
		final RevCommit f = commit(e
		final RevCommit g = commit(a);
		final RevCommit h = commit(f);
		final RevCommit i = commit(h);

		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.lookupCommit(i.getId()));
		pw.markStart(pw.lookupCommit(g.getId()));

		PlotCommitList<PlotLane> pcl = new PlotCommitList<PlotLane>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);
		CommitListAssert test = new CommitListAssert(pcl);
		test.commit(i).lanePos(1).parents(h);
		test.commit(h).lanePos(1).parents(f);
		test.commit(g).lanePos(0).parents(a);
		test.commit(f).lanePos(1).parents(e
		test.commit(e).lanePos(0).parents(c);
		test.commit(d).lanePos(1).parents(b);
		test.commit(c).lanePos(0).parents(b);
		test.commit(b).lanePos(1).parents(a);
		test.commit(a).lanePos(2).parents();
	}

