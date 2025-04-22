		thrown.expectMessage(Matchers.containsString(
					"want " + unreachable.name() + " not valid"));
		uploadPackV2(
			RequestPolicy.REACHABLE_COMMIT,
			null,
			null,
			"command=fetch\n",
			PacketLineIn.DELIM,
			"want " + unreachable.name() + "\n",
			PacketLineIn.END);
