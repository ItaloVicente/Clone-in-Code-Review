		continueActiveLanes(currCommit);
	}

	private void continueActiveLanes(final PlotCommit currCommit) {
		for (PlotLane lane : activeLanes)
			if (lane != currCommit.lane)
				currCommit.addPassingLane(lane);
