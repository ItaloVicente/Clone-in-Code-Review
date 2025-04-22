
		private void copy() throws IOException {
			try {
				if (len > 0) {
					dst.write(buffer
					len = 0;
				}
				if (flush) {
					flush = false;
					dst.flush();
				}
			} finally {
				if (close) {
					copy = false;
					dst.close();
				}
			}
		}
	}

	private static InterruptedIOException interrupted(InterruptedException c) {
		InterruptedIOException e = new InterruptedIOException();
		e.initCause(c);
		return e;
