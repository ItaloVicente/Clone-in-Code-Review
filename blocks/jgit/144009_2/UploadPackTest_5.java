		TransportException e = assertThrows(TransportException.class
				() -> uploadPackV2(RequestPolicy.REACHABLE_COMMIT_TIP
						new RejectAllRefFilter()
						PacketLineIn.delimiter()
						"want " + unreachable.name() + "\n"
						PacketLineIn.end()));
		assertThat(e.getMessage()
				containsString("want " + unreachable.name() + " not valid"));
