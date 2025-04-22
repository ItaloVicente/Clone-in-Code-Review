	private static void addBlockedPosition(BitSet blockedPositions
			final PlotCommit rObj) {
		if (rObj != null) {
			PlotLane lane = rObj.getLane();
			if (lane != null)
				blockedPositions.set(lane.getPosition());
		}
	}

