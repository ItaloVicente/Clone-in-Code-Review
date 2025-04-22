		ReadableChannel rc = null;
		try {
			long position = 12;
			long remaining = length - (12 + 20);
			while (0 < remaining) {
				DfsBlock b;
				if (rc != null) {
					b = cache.getOrLoad(this
				} else {
					b = cache.get(key
					if (b == null) {
						rc = ctx.db.openFile(packDesc
						int sz = ctx.getOptions().getStreamPackBufferSize();
						if (sz > 0) {
							rc.setReadAheadBytes(sz);
						}
						b = cache.getOrLoad(this
					}
				}

				int ptr = (int) (position - b.start);
				int n = (int) Math.min(b.size() - ptr
				b.write(out
				position += n;
				remaining -= n;
			}
		} finally {
			if (rc != null) {
				rc.close();
			}
