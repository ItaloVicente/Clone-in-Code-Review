		uploadPackV2(
			RequestPolicy.TIP,
			new RejectAllRefFilter(),
			null,
			"command=fetch\n",
			PacketLineIn.delimiter(),
			"want " + tip.name() + "\n",
				PacketLineIn.end());
