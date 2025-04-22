
	/**
	 * Result value of
	 * {@link RepeatingFilePasswordProvider#handleDecodeAttemptResult(String, String, Exception)}.
	 */
	public enum ResourceDecodeResult {
		/** Re-throw the decoding exception. */
		TERMINATE,
		/** Retry the decoding process - including password prompt. */
		RETRY,
		/** Skip attempt and see if we can proceed without the key. */
		IGNORE;
	}

	/**
	 * Invoked to inform the password provider about the decoding result.
	 * <b>Note:</b> any exception thrown from this method (including if called
	 * to inform about success) will be propagated instead of the original (if
	 * any was reported)
	 *
	 * @param resourceKey
	 *            The resource key representing the <U>private</U> file
	 * @param password
	 *            The password that was attempted
	 * @param err
	 *            The attempt result - {@code null} for success
	 * @return How to proceed in case of error - <u>ignored</u> if invoked in
	 *         order to report success. <b>Note:</b> {@code null} is same as
	 *         {@link ResourceDecodeResult#TERMINATE}.
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	ResourceDecodeResult handleDecodeAttemptResult(String resourceKey,
			String password, Exception err)
			throws IOException, GeneralSecurityException;
