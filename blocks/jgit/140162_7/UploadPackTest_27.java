	public void testV2CapabilitiesRefInWantNotAdvertisedIfAdvertisingForbidden()
			throws Exception {
		server.getConfig().setBoolean("uploadpack"
				true);
		server.getConfig().setBoolean("uploadpack"
				false);
		ByteArrayInputStream recvStream = uploadPackV2Setup(null
				PacketLineIn.END);
