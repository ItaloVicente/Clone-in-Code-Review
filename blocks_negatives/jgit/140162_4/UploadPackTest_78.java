		uploadPackV2(
			RequestPolicy.ANY,
			null,
			null,
			"command=fetch\n",
			PacketLineIn.DELIM,
			"want " + unreachable.name() + "\n",
			PacketLineIn.END);
