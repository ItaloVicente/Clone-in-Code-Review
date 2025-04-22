		receiveHeader(new PacketLineReader(pckIn));
		nonceStatus = nonceGenerator != null
				? nonceGenerator.verify(
					receivedNonce
				: NonceStatus.UNSOLICITED;
	}

	private void receiveHeader(StringReader reader) throws IOException {
