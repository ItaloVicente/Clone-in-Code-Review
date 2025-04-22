		thrown.expectMessage(Matchers
				.containsString("want " + unreachable.name() + " not valid"));
		uploadPackV2(RequestPolicy.REACHABLE_COMMIT_TIP
				new RejectAllRefFilter()
				PacketLineIn.DELIM
				PacketLineIn.END);
