	/**
	 * @return the error stream for the channel, the stream is used to detect
	 *         specific error reasons for exceptions.
	 */
	private static OutputStream createErrorStream() {
		return new OutputStream() {
			private StringBuilder all = new StringBuilder();

			private StringBuilder sb = new StringBuilder();

			public String toString() {
				String r = all.toString();
				while (r.endsWith("\n"))
					r = r.substring(0, r.length() - 1);
				return r;
			}

			@Override
			public void write(final int b) throws IOException {
				if (b == '\r') {
					return;
				}

				sb.append((char) b);

				if (b == '\n') {
					all.append(sb);
					sb.setLength(0);
				}
			}
		};
	}

	NoRemoteRepositoryException cleanNotFound(NoRemoteRepositoryException nf) {
		String why = errStream.toString();
