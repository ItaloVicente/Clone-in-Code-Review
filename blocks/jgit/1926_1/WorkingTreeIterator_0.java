
	private static final class IteratorState {
		final WorkingTreeOptions options;

		final CharsetEncoder nameEncoder;

		MessageDigest contentDigest;

		byte[] contentReadBuffer;

		IteratorState(WorkingTreeOptions options) {
			this.options = options;
			this.nameEncoder = Constants.CHARSET.newEncoder();
		}

		void initializeDigestAndReadBuffer() {
			if (contentDigest == null) {
				contentDigest = Constants.newMessageDigest();
				contentReadBuffer = new byte[BUFFER_SIZE];
			}
		}
	}
