		uploadPackV2(
			RequestPolicy.ADVERTISED,
			null,
			null,
			"command=fetch\n",
			PacketLineIn.DELIM,
			"want " + advertized.name() + "\n",
			PacketLineIn.END);
