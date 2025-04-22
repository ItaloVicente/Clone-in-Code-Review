	FileDiffRegion[] getFileRegions() {
		return fileRegions;
	}

	private int findRegionIndex(int offset) {
		DiffRegion key = new DiffRegion(offset, 0);
		return Arrays.binarySearch(regions, key, (a, b) -> {
			if (!TextUtilities.overlaps(a, b)) {
				return a.getOffset() - b.getOffset();
			}
			return 0;
		});
	}

	DiffRegion findRegion(int offset) {
		int i = findRegionIndex(offset);
		return i >= 0 ? regions[i] : null;
	}

	FileDiffRegion findFileRegion(int offset) {
		FileDiffRegion key = new FileDiffRegion(null, null, offset, 0);
		int i = Arrays.binarySearch(fileRegions, key, (a, b) -> {
			if (!TextUtilities.overlaps(a, b)) {
				return a.getOffset() - b.getOffset();
			}
			return 0;
		});
		return i >= 0 ? fileRegions[i] : null;
