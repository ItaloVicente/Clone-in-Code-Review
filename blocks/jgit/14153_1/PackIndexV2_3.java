	private int getLevelTwo(final long nthPosition
		final long base = levelOne > 0 ? fanoutTable[levelOne - 1] : 0;
		return (int) (nthPosition - base);
	}

	@Override
	public ObjectId getObjectId(final long nthPosition) {
		final int levelOne = findLevelOne(nthPosition);
		final int p = getLevelTwo(nthPosition
