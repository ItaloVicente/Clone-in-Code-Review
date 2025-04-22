public class RawTextIgnoreLeadingWhitespace extends RawText {
	/** Creates RawText that ignores only leading whitespace. */
	@SuppressWarnings("hiding")
	public static final Factory FACTORY = new Factory() {
		public RawText create(byte[] input) {
			return new RawTextIgnoreLeadingWhitespace(input);
		}
	};

