		p.position = getFreePosition(blockedPositions);
		activeLanes.add(p);
		return p;
	}

	private int getFreePosition(BitSet blockedPositions) {
		if (freePositions.isEmpty())
			return positionsAllocated++;

		if (blockedPositions != null) {
			for (Integer pos : freePositions)
				if (!blockedPositions.get(pos.intValue())) {
					freePositions.remove(pos);
					return pos.intValue();
				}
			return positionsAllocated++;
