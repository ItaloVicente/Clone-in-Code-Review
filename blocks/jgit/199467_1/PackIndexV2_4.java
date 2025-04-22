	@Override
	public int findPosition(AnyObjectId objId) {
		int levelOne = objId.getFirstByte();
		int levelTwo = binarySearchLevelTwo(objId
		if (levelTwo < 0) {
			return -1;
		}
		long objsBefore = levelOne == 0 ? 0 : fanoutTable[levelOne-1];
		return (int)objsBefore + levelTwo;
	}

