	private void handleBlockedLanes(final int index
			final PlotCommit childOnLane) {
		for (PlotCommit child : currCommit.children) {
			if (child == childOnLane)

			boolean childIsMerge = child.getParent(0) != currCommit;
			if (childIsMerge) {
				PlotLane laneToUse = currCommit.lane;
				laneToUse = handleMerge(index
						laneToUse);
				child.addMergingLane(laneToUse);
			} else {
				PlotLane laneToUse = child.lane;
				currCommit.addForkingOffLane(laneToUse);
			}
		}
	}

	private PlotLane handleMerge(final int index
			final PlotCommit childOnLane


