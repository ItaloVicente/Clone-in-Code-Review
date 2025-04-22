	@Override
	void resolve(Set<ObjectId> matches
			throws IOException {
		int[] data = names[id.getFirstByte()];
		int max = offset32[id.getFirstByte()].length >>> 2;
		int high = max;
		if (high == 0)
			return;
		int low = 0;
		do {
			int p = (low + high) >>> 1;
			final int cmp = id.prefixCompare(data
			if (cmp < 0)
				high = p;
			else if (cmp == 0) {
				while (0 < p && id.prefixCompare(data
					p--;
				for (; p < max && id.prefixCompare(data
					matches.add(ObjectId.fromRaw(data
					if (matches.size() > matchLimit)
						break;
				}
				return;
			} else
				low = p + 1;
		} while (low < high);
	}

	private static int idOffset(int p) {
	}

