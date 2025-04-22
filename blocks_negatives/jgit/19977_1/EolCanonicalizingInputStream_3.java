	private boolean abortIfBinary;

	/**
	 * A special exception thrown when {@link EolCanonicalizingInputStream} is
	 * told to throw an exception when attempting to read a binary file. The
	 * exception may be thrown at any stage during reading.
	 *
	 * @since 3.2
	 */
	public static class IsBinaryException extends IOException {
		private static final long serialVersionUID = 1L;

		IsBinaryException() {
			super();
		}
	}

