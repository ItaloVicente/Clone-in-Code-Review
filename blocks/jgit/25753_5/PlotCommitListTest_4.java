	@Test
	public void testBug419359() throws Exception {
		final RevCommit a1 = commit();
		final RevCommit b1 = commit();
		final RevCommit c = commit(a1);
		final RevCommit b2 = commit(b1);
		final RevCommit a2 = commit(a1);
		final RevCommit d = commit(a2);
		final RevCommit b3 = commit(b2);
		final RevCommit e = commit(d);
		final RevCommit a3 = commit(a2);
		final RevCommit a4 = commit(a3);
		final RevCommit a5 = commit(a3

		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.lookupCommit(b3.getId()));
		pw.markStart(pw.lookupCommit(c.getId()));
		pw.markStart(pw.lookupCommit(e.getId()));
		pw.markStart(pw.lookupCommit(a5.getId()));

		PlotCommitList<PlotLane> pcl = new PlotCommitList<PlotLane>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);

		assertEquals("b2 is an a different position"
				pcl.get(7).lane.position);
		assertEquals("b3 is on a different position"
				pcl.get(4).lane.position);

		assertNotEquals("b lane is blocked by c"
				pcl.get(8).lane.position);
		assertNotEquals("b lane is blocked by a2"
				pcl.get(6).lane.position);
		assertNotEquals("b lane is blocked by d"
				pcl.get(5).lane.position);
	}

	@Test
	public void testMultipleMerges() throws Exception {
		final RevCommit a1 = commit();
		final RevCommit b1 = commit(a1);
		final RevCommit a2 = commit(a1);
		final RevCommit a3 = commit(a2
		final RevCommit b2 = commit(b1);
		final RevCommit a4 = commit(a3
		final RevCommit b3 = commit(b2);

		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.lookupCommit(a4));
		pw.markStart(pw.lookupCommit(b3));
		PlotCommitList<PlotLane> pcl = new PlotCommitList<PlotLane>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);

		Set<Integer> positions = asSet(0
		CommitListAssert test = new CommitListAssert(pcl);
		int posB = test.commit(b3).lanePos(positions).getLanePos();
		int posA = test.commit(a4).lanePos(positions).getLanePos();
		test.commit(b2).lanePos(posB);
		test.commit(a3).lanePos(posA);
		test.commit(a2).lanePos(posA);
		test.commit(b1).lanePos(posB);
		test.commit(a1).lanePos(posA);
		test.noMoreCommits();
	}

	@Test
	public void testMergeBlockedBySelf() throws Exception {
		final RevCommit a1 = commit();
		final RevCommit b1 = commit(a1);
		final RevCommit a2 = commit(a1);
		final RevCommit a3 = commit(a2
		final RevCommit b3 = commit(b2);
		final RevCommit a4 = commit(a3);

		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.lookupCommit(a4));
		pw.markStart(pw.lookupCommit(b3));
		PlotCommitList<PlotLane> pcl = new PlotCommitList<PlotLane>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);

		Set<Integer> positions = asSet(0
		CommitListAssert test = new CommitListAssert(pcl);
		int posA = test.commit(a4).lanePos(positions).getLanePos();
		int posB = test.commit(b3).lanePos(positions).getLanePos();
		test.commit(a3).lanePos(posA);
		test.commit(b2).lanePos(posB);
		test.commit(a2).lanePos(posA);
		test.commit(b1).lanePos(posB);
		test.commit(a1).lanePos(posA);
		test.noMoreCommits();
	}

	@Test
	public void testMergeBlockedByOther() throws Exception {
		final RevCommit a1 = commit();
		final RevCommit b1 = commit(a1);
		final RevCommit a2 = commit(a1);
		final RevCommit a3 = commit(a2
		final RevCommit a4 = commit(a3
		final RevCommit b2 = commit(b1);

		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.lookupCommit(a4));
		pw.markStart(pw.lookupCommit(b2));
		pw.markStart(pw.lookupCommit(c));
		PlotCommitList<PlotLane> pcl = new PlotCommitList<PlotLane>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);

		Set<Integer> positions = asSet(0
		CommitListAssert test = new CommitListAssert(pcl);
		int posB = test.commit(b2).lanePos(positions).getLanePos();
		int posA = test.commit(a4).lanePos(positions).getLanePos();
		test.commit(a3).lanePos(posA);
		test.commit(c).lanePos(positions);
		test.commit(a2).lanePos(posA);
		test.commit(a1).lanePos(posA);
		test.noMoreCommits();
	}

	@Test
	public void testDanglingCommitShouldContinueLane() throws Exception {
		final RevCommit a1 = commit();
		final RevCommit a2 = commit(a1);
		final RevCommit a3 = commit(a2);
		final RevCommit b1 = commit(a1);

		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.lookupCommit(a3));
		pw.markStart(pw.lookupCommit(b1));
		PlotCommitList<PlotLane> pcl = new PlotCommitList<PlotLane>();
		pcl.source(pw);

		Set<Integer> positions = asSet(0
		CommitListAssert test = new CommitListAssert(pcl);
		PlotLane laneB = test.commit(b1).lanePos(positions).current.getLane();
		int posA = test.commit(a3).lanePos(positions).getLanePos();
		test.commit(a2).lanePos(posA);
		assertArrayEquals(
				"Although the parent of b1
				new PlotLane[] { laneB }
		test.noMoreCommits();
	}
