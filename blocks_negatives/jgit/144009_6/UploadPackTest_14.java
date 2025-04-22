		thrown.expect(PackProtocolException.class);
		thrown.expectMessage("unexpected filter blob:limit=5");
		uploadPackV2(
			"command=fetch\n",
			PacketLineIn.delimiter(),
			"want " + commit.toObjectId().getName() + "\n",
			"filter blob:limit=5\n",
			"done\n",
				PacketLineIn.end());
