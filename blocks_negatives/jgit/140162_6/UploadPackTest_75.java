		thrown.expectMessage(Matchers.containsString(
					"want " + parentOfTip.name() + " not valid"));
		uploadPackV2(
			RequestPolicy.TIP,
			new RejectAllRefFilter(),
			null,
			"command=fetch\n",
			PacketLineIn.DELIM,
			"want " + parentOfTip.name() + "\n",
			PacketLineIn.END);
