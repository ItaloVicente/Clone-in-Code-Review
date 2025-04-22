		uploadPackV2(
			RequestPolicy.REACHABLE_COMMIT,
			null,
			null,
			"command=fetch\n",
			PacketLineIn.DELIM,
			"want " + reachable.name() + "\n",
			PacketLineIn.END);
