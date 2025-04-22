		names.put(KEEP
	}

	private static ConcurrentHashMap<PackExt
		ConcurrentHashMap<PackExt
		names.put(PACK
		for (PackExt ext : PackExt.values()) {
			if ((extensions & ext.getBit()) != 0) {
				names.put(ext
			}
		}
		return names;
