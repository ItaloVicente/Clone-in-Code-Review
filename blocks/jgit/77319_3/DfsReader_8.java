	private long getObjectSizeImpl(PackList packList
			throws IOException {
		for (DfsPackFile pack : packList.packs) {
			if (pack == last) {
				continue;
			}
			long sz = pack.getObjectSize(this
			if (0 <= sz) {
				last = pack;
				return sz;
			}
		}
		return -1;
	}

