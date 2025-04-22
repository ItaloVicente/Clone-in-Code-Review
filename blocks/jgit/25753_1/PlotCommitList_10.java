	private void handleBlockedLanes(final int index
			final PlotCommit childOnLane) {

		for (PlotCommit child : currCommit.children) {
			if (child == childOnLane)

			boolean childIsMerge = child.getParent(0) != currCommit;
			PlotLane laneToUse = childIsMerge ? currCommit.lane : child.lane;
			if (childIsMerge) {
				laneToUse = handleMerge(index
						laneToUse);
				child.addMergingLane(laneToUse);
			} else {


				currCommit.addForkingOffLane(laneToUse);
			}

			drawLaneToChild(index
		}

		if (childOnLane != null)
			drawLaneToChild(index
	}

	private PlotLane handleMerge(final int index
			final PlotCommit childOnLane


