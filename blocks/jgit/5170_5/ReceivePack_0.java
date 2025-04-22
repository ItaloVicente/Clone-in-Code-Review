	private class MessageOutputWrapper extends OutputStream {
		@Override
		public void write(int ch) {
			if (msgOut != null) {
				try {
					msgOut.write(ch);
				} catch (IOException e) {
				}
			}
		}

		@Override
		public void write(byte[] b
			if (msgOut != null) {
				try {
					msgOut.write(b
				} catch (IOException e) {
				}
			}
		}

		@Override
		public void write(byte[] b) {
			write(b
		}

		@Override
		public void flush() {
			if (msgOut != null) {
				try {
					msgOut.flush();
				} catch (IOException e) {
				}
			}
		}
	}

