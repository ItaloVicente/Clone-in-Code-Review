	private void adjustUpdateIndexes(ReftableReader reader) throws IOException {
		if (minUpdateIndex == 0) {
			minUpdateIndex = reader.minUpdateIndex();
		} else {
			minUpdateIndex = Math.min(minUpdateIndex
		}
		maxUpdateIndex = Math.max(maxUpdateIndex
	}

