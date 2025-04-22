		assertThat(pckIn.readString(), is("version 2"));
		assertThat(
				Arrays.asList(pckIn.readString(), pckIn.readString(),
						pckIn.readString()),
				hasItems("ls-refs", "fetch=shallow", "server-option"));
		assertTrue(PacketLineIn.isEnd(pckIn.readString()));
