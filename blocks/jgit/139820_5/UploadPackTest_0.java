	public void testV2CapabilitiesRefInWantNotAdvertisedIfUnallowed()
			throws Exception {
		server.getConfig().setBoolean("uploadpack"
				false);
		ByteArrayInputStream recvStream = uploadPackV2Setup(null
				PacketLineIn.end());
