						if (revidx == null) {
							revidx = new PackReverseIndex(idx);
						}
						PackBitmapIndex bmidx;
						int sz;
						ctx.stats.readBitmap++;
						long start = System.nanoTime();
						try (ReadableChannel rc = ctx.db.openFile(desc
								BITMAP_INDEX)) {
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
								bmidx = PackBitmapIndex.read(in
							} finally {
								size = rc.position();
								ctx.stats.readIdxBytes += size;
								ctx.stats.readIdxMicros += elapsedMicros(start);
							}
							sz = (int) Math.min(
									size + idx.getObjectCount() * REC_SIZE + 8
									Integer.MAX_VALUE);
						} catch (EOFException e) {
							throw new IOException(MessageFormat.format(
									DfsText.get().shortReadOfIndex
									desc.getFileName(BITMAP_INDEX))
						} catch (IOException e) {
							throw new IOException(MessageFormat.format(
									DfsText.get().cannotReadIndex
									desc.getFileName(BITMAP_INDEX))
						}
						return new DfsBlockCache.Ref<>(idxKey
								new CachedIndices(idx
					});
			if (indices != null) {
				cachedIndices = indices;
				return indices.bitmapIdx;
