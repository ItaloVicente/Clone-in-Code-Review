		thrown.expect(TransportException.class);
		thrown.expectMessage(Matchers.containsString(
					"want " + parentOfTip.name() + " not valid"));
		uploadPackV2(
			RequestPolicy.TIP,
			new RejectAllRefFilter(),
			null,
			"command=fetch\n",
			PacketLineIn.delimiter(),
			"want " + parentOfTip.name() + "\n",
				PacketLineIn.end());
