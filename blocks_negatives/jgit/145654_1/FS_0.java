		private static void checkTimeout(FileStore s, long start)
				throws TimeoutException {
			if (System.nanoTime() - start >= FALLBACK_TIMESTAMP_RESOLUTION
					.toNanos()) {
				throw new TimeoutException(MessageFormat.format(JGitText
						.get().timeoutMeasureFsTimestampResolution,
						s.toString()));
