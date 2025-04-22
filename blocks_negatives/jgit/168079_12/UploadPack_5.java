	 * @param input
	 *            raw input to read client commands from. Caller must ensure the
	 *            input is buffered, otherwise read performance may suffer.
	 * @param output
	 *            response back to the Git network client, to write the pack
	 *            data onto. Caller must ensure the output is buffered,
	 *            otherwise write performance may suffer.
	 * @param messages
	 *            secondary "notice" channel to send additional messages out
	 *            through. When run over SSH this should be tied back to the
	 *            standard error channel of the command execution. For most
	 *            other network connections this should be null.
	 * @throws ServiceMayNotContinueException
	 *             thrown if one of the hooks throws this.
	 * @throws IOException
	 *             thrown if the server or the client I/O fails, or there's an
	 *             internal server error.
