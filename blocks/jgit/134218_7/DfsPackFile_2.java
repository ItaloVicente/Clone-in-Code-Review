	private long copyPackBypassCache(PackOutputStream out
			throws IOException {
		ByteBuffer buf = newCopyBuffer(out
		long position = 12;
		long remaining = length - (12 + 20);
		boolean packHeadSkipped = false;
		while (0 < remaining) {
			DfsBlock b = cache.get(key
			if (b != null) {
