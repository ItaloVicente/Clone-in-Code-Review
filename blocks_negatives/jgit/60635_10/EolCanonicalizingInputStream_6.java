public class EolCanonicalizingInputStream extends InputStream {
	private final byte[] single = new byte[1];

	private final byte[] buf = new byte[8096];

	private final InputStream in;

	private int cnt;

	private int ptr;

	private boolean isBinary;

	private boolean detectBinary;

	private boolean abortIfBinary;

	/**
	 * A special exception thrown when {@link EolCanonicalizingInputStream} is
	 * told to throw an exception when attempting to read a binary file. The
	 * exception may be thrown at any stage during reading.
	 *
	 * @since 3.3
	 */
	public static class IsBinaryException extends IOException {
		private static final long serialVersionUID = 1L;

		IsBinaryException() {
			super();
		}
	}
