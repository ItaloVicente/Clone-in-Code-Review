
	private DfsBlockCache.Ref<PackIndex> loadPackIndex(
			DfsReader ctx
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
				int sz = (int) Math.min(
						idx.getObjectCount() * REC_SIZE
						Integer.MAX_VALUE);
				ctx.stats.readIdxBytes += rc.position();
				index = idx;
				return new DfsBlockCache.Ref<>(idxKey
			} finally {
				ctx.stats.readIdxMicros += elapsedMicros(start);
			}
		} catch (EOFException e) {
			throw new IOException(MessageFormat.format(
					DfsText.get().shortReadOfIndex
					desc.getFileName(INDEX))
		} catch (IOException e) {
			throw new IOException(MessageFormat.format(
					DfsText.get().cannotReadIndex
					desc.getFileName(INDEX))
		}
	}

	private DfsBlockCache.Ref<PackReverseIndex> loadReverseIdx(
			DfsReader ctx
		PackReverseIndex revidx = new PackReverseIndex(idx);
		int sz = (int) Math.min(idx.getObjectCount() * 8
		reverseIndex = revidx;
		return new DfsBlockCache.Ref<>(revKey
	}

	private DfsBlockCache.Ref<PackBitmapIndex> loadBitmapIndex(
			DfsReader ctx
			DfsStreamKey bitmapKey
			PackIndex idx
			PackReverseIndex revidx) throws IOException {
		ctx.stats.readBitmap++;
		long start = System.nanoTime();
		try (ReadableChannel rc = ctx.db.openFile(desc
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
				in = new BufferedInputStream(in
				bmidx = PackBitmapIndex.read(in
			} finally {
				size = rc.position();
				ctx.stats.readIdxBytes += size;
				ctx.stats.readIdxMicros += elapsedMicros(start);
			}
			int sz = (int) Math.min(size
			bitmapIndex = bmidx;
			return new DfsBlockCache.Ref<>(bitmapKey
		} catch (EOFException e) {
			throw new IOException(MessageFormat.format(
					DfsText.get().shortReadOfIndex
					desc.getFileName(BITMAP_INDEX))
		} catch (IOException e) {
			throw new IOException(MessageFormat.format(
					DfsText.get().cannotReadIndex
					desc.getFileName(BITMAP_INDEX))
		}
	}
