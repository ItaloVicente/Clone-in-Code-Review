		if (written || os != null) {
			throw new IllegalStateException(MessageFormat
					.format(JGitText.get().lockStreamMultiple
		}

		return new OutputStream() {

			private OutputStream out;

			private boolean closed;

			private OutputStream get() throws IOException {
				if (written) {
					throw new IOException(MessageFormat
							.format(JGitText.get().lockStreamMultiple
				}
				if (out == null) {
					os = getStream();
					if (fsync) {
