	private ObjectLoader openImpl(PackList packList
			boolean noGarbage) throws IOException {
		for (DfsPackFile pack : packList.packs) {
			if (pack == last || (noGarbage && pack.isGarbage())) {
				continue;
			}
			ObjectLoader ldr = pack.get(this
			if (ldr != null) {
				last = pack;
				return ldr;
			}
		}
		return null;
	}

