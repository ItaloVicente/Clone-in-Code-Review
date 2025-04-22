
	@Test
	public void testFetchWithSessionID() throws IOException {
		PacketLineIn pckIn = formatAsPacketLine("session-id=the.client.sid"
				PacketLineIn.end());

		ProtocolV2Parser parser = new ProtocolV2Parser(
				ConfigBuilder.start().allowFilter().done());
		FetchV2Request request = parser.parseFetchRequest(pckIn);

		assertEquals("the.client.sid"
	}

	@Test
	public void testLsRefsWithSessionID() throws IOException {
		PacketLineIn pckIn = formatAsPacketLine("session-id=the.client.sid"
				PacketLineIn.delimiter()

		ProtocolV2Parser parser = new ProtocolV2Parser(
				ConfigBuilder.getDefault());
		LsRefsV2Request req = parser.parseLsRefsRequest(pckIn);

		assertEquals("the.client.sid"
	}
