	@Test
	public void testV2CapabilitiesRefInWant() throws Exception {
		server.getConfig().setBoolean("uploadpack"
		ByteArrayInputStream recvStream =
			uploadPackV2Setup(null
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(
			Arrays.asList(pckIn.readString()
			hasItems("ls-refs"
		assertTrue(pckIn.readString() == PacketLineIn.END);
	}

	@Test
	public void testV2CapabilitiesRefInWantNotAdvertisedIfUnallowed() throws Exception {
		server.getConfig().setBoolean("uploadpack"
		ByteArrayInputStream recvStream =
			uploadPackV2Setup(null
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(
			Arrays.asList(pckIn.readString()
			hasItems("ls-refs"
		assertTrue(pckIn.readString() == PacketLineIn.END);
	}

	@Test
	public void testV2CapabilitiesRefInWantNotAdvertisedIfAdvertisingForbidden() throws Exception {
		server.getConfig().setBoolean("uploadpack"
		server.getConfig().setBoolean("uploadpack"
		ByteArrayInputStream recvStream =
			uploadPackV2Setup(null
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(
			Arrays.asList(pckIn.readString()
			hasItems("ls-refs"
		assertTrue(pckIn.readString() == PacketLineIn.END);
	}

