		thrown.expect(PackProtocolException.class);
		thrown.expectMessage("unexpected invalid-argument");
		uploadPackV2(
			"command=ls-refs\n",
			PacketLineIn.delimiter(),
			"invalid-argument\n",
				PacketLineIn.end());
