		thrown.expect(TransportException.class);
		thrown.expectMessage(Matchers.containsString(
					"want " + unreachable.name() + " not valid"));
		uploadPackV2(
			RequestPolicy.REACHABLE_COMMIT_TIP,
			new RejectAllRefFilter(),
			null,
			"command=fetch\n",
			PacketLineIn.delimiter(),
			"want " + unreachable.name() + "\n",
				PacketLineIn.end());
