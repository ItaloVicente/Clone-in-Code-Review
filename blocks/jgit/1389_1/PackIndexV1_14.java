	@Override
	void resolve(Set<ObjectId> matches
			throws IOException {
		byte[] data = idxdata[id.getFirstByte()];
		if (data == null)
			return;
		int max = data.length / (4 + Constants.OBJECT_ID_LENGTH);
		int high = max;
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

	private static int idOffset(int mid) {
		return ((4 + Constants.OBJECT_ID_LENGTH) * mid) + 4;
	}

