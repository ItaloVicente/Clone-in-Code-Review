			idxref = cache.getOrLoadRef(bitmapKey
				ctx.stats.readBitmap++;
				long start = System.nanoTime();
				try (ReadableChannel rc = ctx.db.openFile(desc
					long size;
					PackBitmapIndex idx;
					try {
						InputStream in = Channels.newInputStream(rc);
						int wantSize = 8192;
						int bs = rc.blockSize();
						if (0 < bs && bs < wantSize)
							bs = (wantSize / bs) * bs;
						else if (bs <= 0)
							bs = wantSize;
						in = new BufferedInputStream(in
						idx = PackBitmapIndex.read(in
								getReverseIdx(ctx));
					} finally {
						size = rc.position();
						ctx.stats.readIdxBytes += size;
						ctx.stats.readIdxMicros += elapsedMicros(start);
					}
					int sz = (int) Math.min(size
					return new DfsBlockCache.Ref<>(bitmapKey
				} catch (EOFException e) {
					throw new IOException(
							MessageFormat.format(DfsText.get().shortReadOfIndex
									desc.getFileName(BITMAP_INDEX))
							e);
				} catch (IOException e) {
					throw new IOException(
							MessageFormat.format(DfsText.get().cannotReadIndex
									desc.getFileName(BITMAP_INDEX))
							e);
