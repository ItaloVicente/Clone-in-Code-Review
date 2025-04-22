	/**
	 * Sends the initial messages starting the ssh setup: the client
	 * identification and the KEX init message.
	 *
	 * @return the client's KEX seed
	 * @throws IOException
	 *             if something goes wrong
	 */
	private byte[] sendStartSsh() throws IOException {
		super.sendIdentification(clientVersion);
		return super.sendKexInit();
	}

