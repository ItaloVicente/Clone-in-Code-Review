	private DfsBlockCache.Ref<PackIndex> loadIdx(DfsReader ctx
			DfsStreamKey key) throws IOException {
		try {
			Repository.getGlobalListenerList()
					.dispatch(new BeforeDfsPackIndexLoadedEvent(this));

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
				int sz = (int) Math.min(idx.getObjectCount() * REC_SIZE
						Integer.MAX_VALUE);
				ctx.stats.readIdxBytes += rc.position();
				return new DfsBlockCache.Ref<>(key
			} finally {
				ctx.stats.readIdxMicros += elapsedMicros(start);
			}
		} catch (EOFException e) {
			throw new IOException(
					MessageFormat.format(DfsText.get().shortReadOfIndex
							desc.getFileName(INDEX))
					e);
		} catch (IOException e) {
			throw new IOException(MessageFormat.format(
					DfsText.get().cannotReadIndex
		}
	}

