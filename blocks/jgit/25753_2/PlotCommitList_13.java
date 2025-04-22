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
			}

			if (needDetour) {
				laneToUse = nextFreeLane(blockedPositions);
				currCommit.addForkingOffLane(laneToUse);
				closeLane(laneToUse);
			} else {
				int newPos = getFreePosition(blockedPositions);
				freePositions.add(Integer.valueOf(laneToUse
						.getPosition()));
				laneToUse.position = newPos;
