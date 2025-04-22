			int newPos = -1;
			for (Integer pos : freePositions)
				if (!blockedPositions.get(pos.intValue())) {
					newPos = pos.intValue();
					break;
				}
			if (newPos == -1)
				newPos = positionsAllocated++;
