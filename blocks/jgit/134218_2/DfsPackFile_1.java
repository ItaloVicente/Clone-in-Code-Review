	private void copyPackThroughCache(PackOutputStream out
			ReadableChannel rc) throws IOException {
		long position = 12;
		long remaining = length - (12 + 20);
		boolean miss = false;
		while (0 < remaining) {
			DfsBlock b;
			if (miss) {
				b = cache.getOrLoad(this
			} else {
				b = cache.get(key
				if (b == null) {
					miss = true;
					b = cache.getOrLoad(this
