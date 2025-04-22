		thrown.expect(PackProtocolException.class);
		thrown.expectMessage("unexpected invalid-argument");
		uploadPackV2(
			"command=ls-refs\n",
			PacketLineIn.DELIM,
			"invalid-argument\n",
			PacketLineIn.END);
