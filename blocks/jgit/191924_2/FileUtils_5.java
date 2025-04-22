	@FunctionalInterface
	public interface IOFunction<A

		B apply(A t) throws Exception;
	}

	private static void backOff(long delay
			throws IOException {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			IOException interruption = new InterruptedIOException();
			interruption.addSuppressed(cause);
			throw interruption;
		}
	}

	public static <T> T readWithRetries(File file
			IOFunction<File
			throws Exception {
		int maxStaleRetries = 5;
		int retries = 0;
		long backoff = 50;
		while (true) {
			try {
				try {
					return reader.apply(file);
				} catch (IOException e) {
					if (FileUtils.isStaleFileHandleInCausalChain(e)
							&& retries < maxStaleRetries) {
						if (LOG.isDebugEnabled()) {
							LOG.debug(MessageFormat.format(
									JGitText.get().packedRefsHandleIsStale
									Integer.valueOf(retries))
						}
						retries++;
						continue;
					}
					throw e;
				}
			} catch (FileNotFoundException noFile) {
				if (!file.isFile()) {
					return null;
				}
				if (backoff > 1000) {
					throw noFile;
				}
				backOff(backoff
			}
		}
	}

