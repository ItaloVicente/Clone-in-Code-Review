	private long copyPackBypassCache(PackOutputStream out, DfsReader ctx)
			throws IOException {
		try (ReadableChannel rc = ctx.db.openFile(desc, PACK)) {
			ByteBuffer buf = newCopyBuffer(out, rc);
			if (ctx.getOptions().getStreamPackBufferSize() > 0) {
				rc.setReadAheadBytes(ctx.getOptions().getStreamPackBufferSize());
