		assertEquals(-1
	}

	@Test
	public void testFetchWithTreeDepthFilter() throws IOException {
		PacketLineIn pckIn = formatAsPacketLine(PacketLineIn.DELIM
				"filter tree:3"
				PacketLineIn.END);
		ProtocolV2Parser parser = new ProtocolV2Parser(
				ConfigBuilder.start().allowFilter().done());
		FetchV2Request request = parser.parseFetchRequest(pckIn);
		assertEquals(-1
		assertEquals(3
