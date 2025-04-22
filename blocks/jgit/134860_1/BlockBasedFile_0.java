
	private class LazyChannel implements AutoCloseable
		final DfsReader ctx;
		ReadableChannel rc = null;

		LazyChannel(DfsReader ctx) {
			this.ctx = ctx;
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
