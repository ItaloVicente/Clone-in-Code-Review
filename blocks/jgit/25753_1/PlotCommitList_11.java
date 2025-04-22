			if (rObj == child) {
				childIndex = r;
				break;
			}
			addBlockedPosition(blockedPositions
		}


		if (blockedPositions.get(laneToUse.getPosition())) {

			boolean needDetour = false;
			if (childOnLane != null) {
				for (int r = index - 1; r > childIndex; r--) {
					final PlotCommit rObj = get(r);
					if (rObj == childOnLane) {
						needDetour = true;
						break;
					}
				}
