		StreamRewritingThread(final String cmd, final InputStream in) {
			super("JGit " + cmd + " Errors");
			this.in = in;
		}

		public void run() {
			final byte[] tmp = new byte[512];
			try {
				for (;;) {
					final int n = in.read(tmp);
					if (n < 0)
						break;
					System.err.write(tmp, 0, n);
					System.err.flush();
				}
			} catch (IOException err) {
			} finally {
