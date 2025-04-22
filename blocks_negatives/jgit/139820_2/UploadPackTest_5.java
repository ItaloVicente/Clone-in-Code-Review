		thrown.expect(PackProtocolException.class);
		thrown.expectMessage("unexpected invalid-argument");
		uploadPackV2(
			"command=fetch\n",
			PacketLineIn.DELIM,
			"invalid-argument\n",
			PacketLineIn.END);
