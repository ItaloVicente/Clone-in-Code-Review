
	private static class LazyChannel
			implements AutoCloseable
		private final DfsReader ctx;
		private final DfsPackDescription desc;
		private final PackExt ext;

		private ReadableChannel rc = null;

		LazyChannel(DfsReader ctx
			this.ctx = ctx;
			this.desc = desc;
			this.ext = ext;
		}

		@Override
		public ReadableChannel get() throws IOException {
			if (rc == null) {
				synchronized (this) {
					if (rc == null) {
						rc = ctx.db.openFile(desc
					}
				}
			}
			return rc;
		}

		@Override
		public void close() throws IOException {
			if (rc != null) {
				rc.close();
			}
		}
	}
