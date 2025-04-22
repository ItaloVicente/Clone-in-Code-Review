	@Override
	long getOffset(long nthPosition) {
		final int levelOne = findLevelOne(nthPosition);
		final int levelTwo = getLevelTwo(nthPosition
		final int p = (4 + Constants.OBJECT_ID_LENGTH) * levelTwo;
		return NB.decodeUInt32(idxdata[levelOne]
	}

