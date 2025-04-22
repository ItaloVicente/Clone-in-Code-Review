	/**
	 * Send an error message to the client.
	 * <p>
	 * If any error messages are sent before the references are advertised to
	 * the client, the errors will be sent instead of the advertisement and the
	 * receive operation will be aborted. All clients should receive and display
	 * such early stage errors.
	 * <p>
	 * If the reference advertisements have already been sent, messages are sent
	 * in a side channel. If the client doesn't support receiving messages, the
	 * message will be discarded, with no other indication to the caller or to
	 * the client.
	 * <p>
	 * {@link PreReceiveHook}s should always try to use
	 * {@link ReceiveCommand#setResult(Result, String)} with a result status of
	 * {@link Result#REJECTED_OTHER_REASON} to indicate any reasons for
	 * rejecting an update. Messages attached to a command are much more likely
	 * to be returned to the client.
	 *
	 * @param what
	 *            string describing the problem identified by the hook. The
	 *            string must not end with an LF, and must not contain an LF.
	 */
