		return spawn(cmd, null);
	}

	/**
	 * Spawn process
	 *
	 * @param cmd
	 *            command
	 * @param protocolVersion
	 *            to use
	 * @return a {@link java.lang.Process} object.
	 * @throws org.eclipse.jgit.errors.TransportException
	 *             if any.
	 */
	private Process spawn(String cmd,
			TransferConfig.ProtocolVersion protocolVersion)
			throws TransportException {
