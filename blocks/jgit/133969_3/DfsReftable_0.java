		private static final class LazyReadableChannel
				implements ReadableChannel {
			private final DfsReader ctx;
			private final DfsReftable file;
			private ReadableChannel ch;

			LazyReadableChannel(DfsReftable file
				this.file = file;
				this.ctx = ctx;
			}

			private ReadableChannel channel() throws IOException {
				if (ch == null) {
					ch = ctx.db.openFile(file.desc
				}
				return ch;
			}

			@Override
			public int blockSize() {
