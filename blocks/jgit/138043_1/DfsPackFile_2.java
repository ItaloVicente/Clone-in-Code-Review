				idxref = cache.getOrLoadRef(idxKey
					try {
						ctx.stats.readIdx++;
						long start = System.nanoTime();
						try (ReadableChannel rc = ctx.db.openFile(desc
								INDEX)) {
							InputStream in = Channels.newInputStream(rc);
							int wantSize = 8192;
							int bs = rc.blockSize();
							if (0 < bs && bs < wantSize) {
								bs = (wantSize / bs) * bs;
							} else if (bs <= 0) {
								bs = wantSize;
