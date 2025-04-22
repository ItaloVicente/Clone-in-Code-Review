		ctx.stats.readBitmap++;
		long start = System.nanoTime();
		try (ReadableChannel rc = ctx.db.openFile(desc, BITMAP_INDEX)) {
			long size;
			PackBitmapIndex bmidx;
			try {
				InputStream in = Channels.newInputStream(rc);
				int wantSize = 8192;
				int bs = rc.blockSize();
				if (0 < bs && bs < wantSize) {
					bs = (wantSize / bs) * bs;
				} else if (bs <= 0) {
					bs = wantSize;
				}
				in = new BufferedInputStream(in, bs);
				bmidx = PackBitmapIndex.read(in, idx, revidx);
			} finally {
				size = rc.position();
				ctx.stats.readIdxBytes += size;
				ctx.stats.readIdxMicros += elapsedMicros(start);
			}
			int sz = (int) Math.min(size, Integer.MAX_VALUE);
			return new DfsBlockCache.Ref<>(key, 0, sz, bmidx);
		} catch (EOFException e) {
			throw new IOException(
					MessageFormat.format(DfsText.get().shortReadOfIndex,
							desc.getFileName(BITMAP_INDEX)),
					e);
		} catch (IOException e) {
			throw new IOException(
					MessageFormat.format(DfsText.get().cannotReadIndex,
							desc.getFileName(BITMAP_INDEX)),
					e);
		}
