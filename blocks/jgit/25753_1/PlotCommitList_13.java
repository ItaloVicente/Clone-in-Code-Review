		return laneToUse;
	}

	private void drawLaneToChild(final int commitIndex
			PlotLane laneToContinue) {
		for (int r = commitIndex - 1; r >= 0; r--) {
			final PlotCommit rObj = get(r);
			if (rObj == child)
				break;
			if (rObj != null)
				rObj.addPassingLane(laneToContinue);
		}
	}

	private static void addBlockedPosition(BitSet blockedPositions
			final PlotCommit rObj) {
		if (rObj != null) {
			PlotLane lane = rObj.getLane();
			if (lane != null)
				blockedPositions.set(lane.getPosition());
			for (PlotLane l : rObj.passingLanes)
				blockedPositions.set(l.getPosition());
			for (PlotLane l : rObj.forkingOffLanes)
				blockedPositions.set(l.getPosition());
			for (PlotLane l : rObj.mergingLanes)
				blockedPositions.set(l.getPosition());
