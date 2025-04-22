		List<Integer> indentations = plotRenderer.indentations;
		assertEquals(indentations.get(2), indentations.get(3));
	}

	private PlotCommitList<PlotLane> createCommitList(ObjectId start)
			throws IOException {
		TestPlotWalk walk = new TestPlotWalk(db);
		walk.markStart(walk.parseCommit(start));
		PlotCommitList<PlotLane> commitList = new PlotCommitList<>();
		commitList.source(walk);
		commitList.fillTo(1000);
		return commitList;
