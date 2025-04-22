		long position = 12;
		long remaining = length - (12 + 20);
		while (0 < remaining) {
			DfsBlock b = cache.getOrLoad(this, position, ctx);
			int ptr = (int) (position - b.start);
			int n = (int) Math.min(b.size() - ptr, remaining);
			b.write(out, position, n);
			position += n;
			remaining -= n;
