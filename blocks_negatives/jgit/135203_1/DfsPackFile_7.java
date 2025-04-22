						in = new BufferedInputStream(in, bs);
						bmidx = PackBitmapIndex.read(in, idx, revidx);
					} finally {
						size = rc.position();
						ctx.stats.readIdxBytes += size;
						ctx.stats.readIdxMicros += elapsedMicros(start);
					}
					int sz = (int) Math.min(size, Integer.MAX_VALUE);
					return new DfsBlockCache.Ref<>(bitmapKey, 0, sz, bmidx);
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
			});
			PackBitmapIndex bmidx = idxref.get();
			if (bmidx != null) {
				bitmapIndex = idxref;
