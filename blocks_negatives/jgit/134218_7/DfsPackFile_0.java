	private void copyPackThroughCache(PackOutputStream out, DfsReader ctx)
			throws IOException {
		ReadableChannel rc = null;
		try {
			long position = 12;
			long remaining = length - (12 + 20);
			while (0 < remaining) {
				DfsBlock b;
				if (rc != null) {
					b = cache.getOrLoad(this, position, ctx, rc);
				} else {
					b = cache.get(key, alignToBlock(position));
					if (b == null) {
						rc = ctx.db.openFile(desc, PACK);
						int sz = ctx.getOptions().getStreamPackBufferSize();
						if (sz > 0) {
							rc.setReadAheadBytes(sz);
						}
						b = cache.getOrLoad(this, position, ctx, rc);
					}
				}
