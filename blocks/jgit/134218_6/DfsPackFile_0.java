		try (ReadableChannel rc = ctx.db.openFile(desc
			int sz = ctx.getOptions().getStreamPackBufferSize();
			if (sz > 0) {
				rc.setReadAheadBytes(sz);
			}
			if (cache.shouldCopyThroughCache(length)) {
				copyPackThroughCache(out
			} else {
				copyPackBypassCache(out
			}
