		try (PlotWalk pw = new PlotWalk(db)) {
			pw.markStart(pw.lookupCommit(c.getId()));

			PlotCommitList<PlotLane> pcl = new PlotCommitList<>();
			pcl.source(pw);
			pcl.fillTo(Integer.MAX_VALUE);

			CommitListAssert test = new CommitListAssert(pcl);
			test.commit(c).lanePos(0).parents(b);
			test.commit(b).lanePos(0).parents(a);
			test.commit(a).lanePos(0).parents();
			test.noMoreCommits();
		}
