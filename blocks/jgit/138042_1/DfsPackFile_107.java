	private void copyPackThroughCache(PackOutputStream out
			ReadableChannel rc) throws IOException {
		long position = 12;
		long remaining = length - (12 + 20);
		while (0 < remaining) {
			DfsBlock b = cache.getOrLoad(this
			int ptr = (int) (position - b.start);
			if (b.size() <= ptr) {
				throw packfileIsTruncated();
