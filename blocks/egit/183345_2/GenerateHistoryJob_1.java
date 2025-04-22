		loadedCommits = new SWTCommitList(resources) {

			@Override
			protected void enter(int index, PlotCommit<SWTLane> currCommit) {
				super.enter(index, currCommit);
				if (wantedIndex < 0 && commitToLoad != null
						&& currCommit.getId().equals(commitToLoad.getId())) {
					wantedIndex = index;
				}
			}
		};
