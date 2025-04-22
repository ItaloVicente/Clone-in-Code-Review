
			if (needDetour) {
				laneToUse = nextFreeLane(blockedPositions);
				currCommit.addForkingOffLane(laneToUse);
				closeLane(laneToUse);
			} else {
				int newPos = getFreePosition(blockedPositions);
				freePositions.add(Integer.valueOf(laneToUse
						.getPosition()));
				laneToUse.position = newPos;
