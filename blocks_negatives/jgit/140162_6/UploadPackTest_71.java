		thrown.expectMessage(Matchers.containsString(
					"want " + unadvertized.name() + " not valid"));
		uploadPackV2(
			RequestPolicy.ADVERTISED,
			null,
			null,
			"command=fetch\n",
			PacketLineIn.DELIM,
			"want " + unadvertized.name() + "\n",
			PacketLineIn.END);
