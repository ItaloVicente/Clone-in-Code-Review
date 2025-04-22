	@Test
	public void testLsRefMinimalReq() throws IOException {
		PacketLineIn pckIn = formatAsPacketLine(PacketLineIn.DELIM
				PacketLineIn.END);

		ProtocolV2Parser parser = new ProtocolV2Parser(
				ConfigBuilder.getDefault());
		LsRefsV2Request req = parser.parseLsRefRequest(pckIn);
		assertFalse(req.getPeel());
		assertFalse(req.getSymrefs());
		assertEquals(0
	}

	@Test
	public void testLsRefSymrefs() throws IOException {
		PacketLineIn pckIn = formatAsPacketLine(PacketLineIn.DELIM
				PacketLineIn.END);

		ProtocolV2Parser parser = new ProtocolV2Parser(
				ConfigBuilder.getDefault());
		LsRefsV2Request req = parser.parseLsRefRequest(pckIn);
		assertFalse(req.getPeel());
		assertTrue(req.getSymrefs());
		assertEquals(0

	}

	@Test
	public void testLsRefPeel() throws IOException {
		PacketLineIn pckIn = formatAsPacketLine(
				PacketLineIn.DELIM
				"peel"
				PacketLineIn.END);

		ProtocolV2Parser parser = new ProtocolV2Parser(
				ConfigBuilder.getDefault());
		LsRefsV2Request req = parser.parseLsRefRequest(pckIn);
		assertTrue(req.getPeel());
		assertFalse(req.getSymrefs());
		assertEquals(0
	}

	@Test
	public void testLsRefRefPrefixes() throws IOException {
		PacketLineIn pckIn = formatAsPacketLine(PacketLineIn.DELIM
				"ref-prefix refs/for"
				PacketLineIn.END);

		ProtocolV2Parser parser = new ProtocolV2Parser(
				ConfigBuilder.getDefault());
		LsRefsV2Request req = parser.parseLsRefRequest(pckIn);
		assertFalse(req.getPeel());
		assertFalse(req.getSymrefs());
		assertEquals(2
		assertThat(req.getRefPrefixes()
	}
