	private void copyPackThroughCache(PackOutputStream out
			ReadableChannel rc) throws IOException {
		long position = 12;
		long remaining = length - (12 + 20);
		while (0 < remaining) {
			DfsBlock b = cache.getOrLoad(this
			int ptr = (int) (position - b.start);
			int n = (int) Math.min(b.size() - ptr
			b.write(out
			position += n;
			remaining -= n;
		}
	}
