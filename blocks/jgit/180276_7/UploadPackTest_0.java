	@Test
	public void testFetchV0CapabilitiesRequestPolicyAny() throws Exception {
		ByteArrayInputStream recvStream = uploadPackSetup(
				TransferConfig.ProtocolVersion.V0.version()
				(UploadPack up) -> {
					up.setRequestPolicy(RequestPolicy.ANY);
				}
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		String packetLine = pckIn.readString();
		assertTrue(packetLine
				.contains(GitProtocolConstants.OPTION_ALLOW_TIP_SHA1_IN_WANT));
		assertTrue(packetLine.contains(
				GitProtocolConstants.OPTION_ALLOW_REACHABLE_SHA1_IN_WANT));
		assertTrue(PacketLineIn.isEnd(pckIn.readString()));
	}

