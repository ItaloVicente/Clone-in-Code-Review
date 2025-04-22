	private static class CopyThread extends Thread {
		private final InputStream src;

		private final OutputStream dst;

		private volatile boolean doFlush;

		CopyThread(final InputStream i, final OutputStream o) {
			setName(Thread.currentThread().getName() + "-Output");
			src = i;
			dst = o;
		}

		void flush() {
			if (!doFlush) {
				doFlush = true;
				interrupt();
			}
		}

		@Override
		public void run() {
			try {
				final byte[] buf = new byte[1024];
				for (;;) {
					try {
						if (doFlush) {
							doFlush = false;
							dst.flush();
						}

						final int n;
						try {
							n = src.read(buf);
						} catch (InterruptedIOException wakey) {
							continue;
						}
						if (n < 0)
							break;
						dst.write(buf, 0, n);
					} catch (IOException e) {
						break;
					}
				}
			} finally {
				try {
					src.close();
				} catch (IOException e) {
				}
				try {
					dst.close();
				} catch (IOException e) {
				}
			}
		}
	}

