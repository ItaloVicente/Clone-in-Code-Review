		try (PlotWalk pw = new PlotWalk(db)) {
			pw.markStart(pw.lookupCommit(i.getId()));
			pw.markStart(pw.lookupCommit(g.getId()));

			PlotCommitList<PlotLane> pcl = new PlotCommitList<>();
			pcl.source(pw);
			pcl.fillTo(Integer.MAX_VALUE);
			Set<Integer> childPositions = asSet(0
			CommitListAssert test = new CommitListAssert(pcl);
			int posI = test.commit(i).lanePos(childPositions).parents(h)
					.getLanePos();
			test.commit(h).lanePos(posI).parents(f);
			test.commit(g).lanePos(childPositions).parents(a);
			test.commit(f).lanePos(posI).parents(e
			test.commit(e).lanePos(posI).parents(c);
			test.commit(d).lanePos(2).parents(b);
			test.commit(c).lanePos(posI).parents(b);
			test.commit(b).lanePos(posI).parents(a);
			test.commit(a).lanePos(0).parents();
		}
