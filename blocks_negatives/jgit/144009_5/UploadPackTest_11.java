		thrown.expect(PackProtocolException.class);
		thrown.expectMessage("No commits selected for shallow request");
		uploadPackV2(
			"command=fetch\n",
			PacketLineIn.delimiter(),
			"deepen-since 1510000\n",
			"want " + tooOld.toObjectId().getName() + "\n",
			"done\n",
				PacketLineIn.end());
