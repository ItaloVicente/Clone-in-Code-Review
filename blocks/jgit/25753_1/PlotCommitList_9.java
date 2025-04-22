			for (int i = 0; i < nChildren; i++) {
				final PlotCommit c = currCommit.children[i];
				PlotCommit firstParent = (PlotCommit) c.getParent(0);
				if (firstParent.lane != null && firstParent.lane != c.lane)
					closeLane(c.lane);
			}
