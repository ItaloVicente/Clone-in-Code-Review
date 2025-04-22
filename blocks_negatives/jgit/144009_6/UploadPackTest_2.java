		uploadPackV2(
			RequestPolicy.ADVERTISED,
			null,
			null,
			"command=fetch\n",
			PacketLineIn.delimiter(),
			"want " + advertized.name() + "\n",
