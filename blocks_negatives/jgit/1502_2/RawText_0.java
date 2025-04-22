public class RawText implements Sequence {
	/** Creates a RawText instance. */
	public static interface Factory {
		/**
		 * Construct a RawText instance for the content.
		 *
		 * @param input
		 *            the content array.
		 * @return a RawText instance wrapping this content.
		 */
		RawText create(byte[] input);
	}

	/** Creates RawText that does not treat whitespace specially. */
	public static final Factory FACTORY = new Factory() {
		public RawText create(byte[] input) {
			return new RawText(input);
		}
	};

