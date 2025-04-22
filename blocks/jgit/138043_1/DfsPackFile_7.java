						in = new BufferedInputStream(in
						bmidx = PackBitmapIndex.read(in
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
				}
			});
