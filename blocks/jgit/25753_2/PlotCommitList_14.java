
		drawLaneToChild(index
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
