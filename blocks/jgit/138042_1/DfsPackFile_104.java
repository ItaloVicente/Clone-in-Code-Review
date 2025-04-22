			DfsBlockCache.Ref<PackBitmapIndex> idxref = cache
					.getOrLoadRef(bitmapKey
						ctx.stats.readBitmap++;
						long start = System.nanoTime();
						try (ReadableChannel rc = ctx.db.openFile(desc
								BITMAP_INDEX)) {
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
									bmidx);
						} catch (EOFException e) {
							throw new IOException(MessageFormat.format(
									DfsText.get().shortReadOfIndex
									desc.getFileName(BITMAP_INDEX))
						} catch (IOException e) {
							throw new IOException(MessageFormat.format(
									DfsText.get().cannotReadIndex
									desc.getFileName(BITMAP_INDEX))
						}
					});
			PackBitmapIndex bmidx = idxref.get();
			if (bitmapIndex == null && bmidx != null) {
				bitmapIndex = bmidx;
