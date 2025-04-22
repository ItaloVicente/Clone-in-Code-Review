		uploadPackV2(
			RequestPolicy.REACHABLE_COMMIT,
			null,
			null,
			"command=fetch\n",
			PacketLineIn.delimiter(),
			"want " + reachable.name() + "\n",
				PacketLineIn.end());
