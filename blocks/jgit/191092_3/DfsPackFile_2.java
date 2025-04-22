	private DfsBlockCache.Ref<PackObjectSizeIndex> loadObjectSizeIdx(
			DfsReader ctx
			DfsStreamKey objIdxKey) throws IOException {
		ctx.stats.readObjSizeIdx++;
		long start = System.nanoTime();
		try (ReadableChannel rc = ctx.db.openFile(desc
				OBJECT_SIZE_INDEX)) {
			long size;
			try {
				InputStream in = Channels.newInputStream(rc);
				int wantSize = 8192;
				int bs = rc.blockSize();
				if (0 < bs && bs < wantSize) {
					bs = (wantSize / bs) * bs;
				} else if (bs <= 0) {
					bs = wantSize;
				}
				in = new BufferedInputStream(in
				objSizeIdx = PackObjectSizeIndexLoader.load(in);
			} finally {
				size = rc.position();
				ctx.stats.readBitmapIdxBytes += size;
				ctx.stats.readBitmapIdxMicros += elapsedMicros(start);
			}
			return new DfsBlockCache.Ref<>(
					objIdxKey
		} catch (EOFException e) {
			throw new IOException(MessageFormat.format(
					DfsText.get().shortReadOfIndex
					desc.getFileName(OBJECT_SIZE_INDEX))
		}

	}

