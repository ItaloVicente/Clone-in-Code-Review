	@Test
	public void testV2CapabilitiesAllowFilter() throws Exception {
		server.getConfig().setBoolean("uploadpack"
		ByteArrayInputStream recvStream =
			uploadPackV2Setup(null
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(
			Arrays.asList(pckIn.readString()
			hasItems("ls-refs"
		assertTrue(pckIn.readString() == PacketLineIn.END);
