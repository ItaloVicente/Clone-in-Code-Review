				if (rObj != null) {
					PlotLane lane = rObj.getLane();
					if (lane != null)
						blockedPositions.set(lane.getPosition());
					rObj.addPassingLane(currCommit.lane);
				}
			}
			if (blockedPositions.get(currCommit.lane.getPosition())) {
				int newPos = -1;
				for (Integer pos : freePositions)
					if (!blockedPositions.get(pos)) {
						newPos = pos;
						break;
					}
				if (newPos == -1)
					newPos = positionsAllocated++;
				freePositions.add(currCommit.lane.getPosition());
				currCommit.lane.position = newPos;
