		thrown.expect(PackProtocolException.class);
		thrown.expectMessage("No commits selected for shallow request");
		uploadPackV2(
			"command=fetch\n",
			PacketLineIn.delimiter(),
			"deepen-not four\n",
			"want " + two.toObjectId().getName() + "\n",
			"done\n",
				PacketLineIn.end());
