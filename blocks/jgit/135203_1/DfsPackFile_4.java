	private PackIndex loadIdx(DfsReader ctx) throws IOException {
		Repository.getGlobalListenerList()
				.dispatch(new BeforeDfsPackIndexLoadedEvent(this));

		try {
			ctx.stats.readIdx++;
			long start = System.nanoTime();
			try (ReadableChannel rc = ctx.db.openFile(desc
				InputStream in = Channels.newInputStream(rc);
				int wantSize = 8192;
				int bs = rc.blockSize();
				if (0 < bs && bs < wantSize) {
					bs = (wantSize / bs) * bs;
				} else if (bs <= 0) {
					bs = wantSize;
				}
				PackIndex idx = PackIndex.read(new BufferedInputStream(in
				ctx.stats.readIdxBytes += rc.position();
