	@Override
	public ChangedPathFilter findBloomFilter(int graphPos) {
		if (noBloomFilters || graphPos < 0 || graphPos > commitCnt) {
			return null;
		}
		int levelOne = findLevelOne(graphPos);
		int p = getLevelTwo(graphPos

		int endIdx = NB.decodeInt32(bloomIdx[levelOne]
		int startIdx = 0;
		if (graphPos > 0) {
			levelOne = findLevelOne(graphPos - 1);
			p = getLevelTwo(graphPos - 1
			startIdx = NB.decodeInt32(bloomIdx[levelOne]
		}

		if (endIdx - startIdx <= 0) {
			return null;
		}
		byte[] data = new byte[endIdx - startIdx];
		System.arraycopy(bloomData
		return new ChangedPathFilter(data
	}

