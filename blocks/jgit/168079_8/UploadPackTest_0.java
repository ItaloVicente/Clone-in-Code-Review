	private ByteArrayInputStream uploadPackV1(
			Consumer<UploadPack> postConstructionSetup
			String... inputLines)
			throws Exception {
		ByteArrayInputStream recvStream =
				uploadPackSetup("1"
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		while (!PacketLineIn.isEnd(pckIn.readString())) {
		}
		return recvStream;
	}

	private ByteArrayInputStream uploadPackV1(String... inputLines) throws Exception {
		return uploadPackV1(null
	}

