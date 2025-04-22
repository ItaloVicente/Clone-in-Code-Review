		PackProtocolException e = assertThrows(PackProtocolException.class
				() -> uploadPackV2("command=ls-refs\n"
						PacketLineIn.delimiter()
						PacketLineIn.end()));
		assertThat(e.getMessage()
				containsString("unexpected invalid-argument"));
