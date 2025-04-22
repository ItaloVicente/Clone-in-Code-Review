		StringReader reader = new PacketLineReader(pckIn);
		receiveSignature(reader);
		if (!reader.read().equals(END_CERT)) {
			throw new PackProtocolException(
					JGitText.get().pushCertificateInvalidSignature);
		}
	}

	private void receiveSignature(StringReader reader) throws IOException {
