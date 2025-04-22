		TransportException e = assertThrows(TransportException.class
				() -> uploadPackV2(RequestPolicy.TIP
						null
						"want " + parentOfTip.name() + "\n"
						PacketLineIn.end()));
		assertThat(e.getMessage()
				containsString("want " + parentOfTip.name() + " not valid"));
