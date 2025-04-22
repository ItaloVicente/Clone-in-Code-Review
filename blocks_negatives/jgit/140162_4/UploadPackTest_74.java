		uploadPackV2(
			RequestPolicy.TIP,
			new RejectAllRefFilter(),
			null,
			"command=fetch\n",
			PacketLineIn.DELIM,
			"want " + tip.name() + "\n",
			PacketLineIn.END);
