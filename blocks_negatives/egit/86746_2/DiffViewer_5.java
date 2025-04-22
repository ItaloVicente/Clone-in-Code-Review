			FileDiffRange key = new FileDiffRange(null, null, offset, offset);
			int i = Arrays.binarySearch(ranges, key, (a, b) -> {
				if (a.getStartOffset() > b.getEndOffset()) {
					return 1;
				}
				if (b.getStartOffset() > a.getEndOffset()) {
					return -1;
				}
				return 0;
			});
			return i >= 0 ? ranges[i] : null;
