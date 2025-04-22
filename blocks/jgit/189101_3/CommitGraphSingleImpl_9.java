		if (noBloomFilter) {
			return null;
		}
		int numHashes = graphData.getNumHashes();
		if (numHashes <= 0) {
			return null;
		}
		return ChangedPathFilter.newBloomKey(path
