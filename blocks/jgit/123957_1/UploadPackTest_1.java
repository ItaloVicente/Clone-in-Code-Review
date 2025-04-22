	@Test
	public void testV2FetchUnrecognizedArgument() throws Exception {
		thrown.expect(PackProtocolException.class);
		thrown.expectMessage("unexpected invalid-argument");
		uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"invalid-argument\n"
			PacketLineIn.END);
	}

