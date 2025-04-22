	private void receive(final ReceivePack rp
			final TemporaryBuffer.Heap inBuf
			throws IOException {
		rp.receive(new ByteArrayInputStream(inBuf.toByteArray())
	}

