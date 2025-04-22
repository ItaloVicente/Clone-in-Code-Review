		PlotWalk pw = new PlotWalk(db);
		pw.markStart(pw.lookupCommit(b3.getId()));
		pw.markStart(pw.lookupCommit(c.getId()));
		pw.markStart(pw.lookupCommit(e.getId()));
		pw.markStart(pw.lookupCommit(a5.getId()));

		PlotCommitList<PlotLane> pcl = new PlotCommitList<>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);

		assertEquals("b2 is an a different position", bPos,
				pcl.get(7).lane.position);
		assertEquals("b3 is on a different position", bPos,
				pcl.get(4).lane.position);

		assertNotEquals("b lane is blocked by c", bPos,
				pcl.get(8).lane.position);
		assertNotEquals("b lane is blocked by a2", bPos,
				pcl.get(6).lane.position);
		assertNotEquals("b lane is blocked by d", bPos,
				pcl.get(5).lane.position);
