		private static String read(Path p) throws IOException {
			final byte[] body = IO.readFully(p.toFile());
			return new String(body
		}

		private static Optional<Duration> measureFsTimestampResolution(
			FileStore s
			LOG.debug("{}: start measure timestamp resolution {} in {}"
					Thread.currentThread()
