	@Override
	public long getOffset(final long nthPosition) {
		final int levelOne = findLevelOne(nthPosition);
		final int levelTwo = getLevelTwo(nthPosition
		return getOffset(levelOne
	}

