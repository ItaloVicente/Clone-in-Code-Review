		TransportException e = assertThrows(TransportException.class
				() -> uploadPackV2(RequestPolicy.ADVERTISED
						"command=fetch\n"
						"want " + unadvertized.name() + "\n"
						PacketLineIn.end()));
		assertThat(e.getMessage()
				containsString("want " + unadvertized.name() + " not valid"));
