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
