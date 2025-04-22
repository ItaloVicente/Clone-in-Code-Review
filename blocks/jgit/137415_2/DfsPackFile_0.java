				DfsStreamKey idxKey = desc.getStreamKey(INDEX);
				DfsBlockCache.Ref<PackIndex> idxref = cache.getOrLoadRef(idxKey
						() -> {
							try {
								ctx.stats.readIdx++;
								long start = System.nanoTime();
								try (ReadableChannel rc = ctx.db.openFile(desc
										INDEX)) {
									InputStream in = Channels
											.newInputStream(rc);
									int wantSize = 8192;
									int bs = rc.blockSize();
									if (0 < bs && bs < wantSize) {
										bs = (wantSize / bs) * bs;
									} else if (bs <= 0) {
										bs = wantSize;
									}
									PackIndex idx = PackIndex.read(
											new BufferedInputStream(in
									int sz = (int) Math.min(
											idx.getObjectCount() * REC_SIZE
											Integer.MAX_VALUE);
									ctx.stats.readIdxBytes += rc.position();
									index = idx;
									return new DfsBlockCache.Ref<>(idxKey
											sz
								} finally {
									ctx.stats.readIdxMicros += elapsedMicros(
											start);
								}
							} catch (EOFException e) {
								throw new IOException(MessageFormat.format(
										DfsText.get().shortReadOfIndex
										desc.getFileName(INDEX))
							} catch (IOException e) {
								throw new IOException(MessageFormat.format(
										DfsText.get().cannotReadIndex
										desc.getFileName(INDEX))
