	private static CanonicalTreeParser readTree(byte[] pathPrefix
			ObjectReader reader
		return new CanonicalTreeParser(pathPrefix
	}

	private void addFirst(int stage
		DirCacheEntry first = toEntry(stage
		beforeAdd(first);
		fastAdd(first);
	}

	private static DirCacheEntry toEntry(int stage
		byte[] buf = i.getEntryPathBuffer();
		int len = i.getEntryPathLength();
		byte[] path = new byte[len];
		System.arraycopy(buf
