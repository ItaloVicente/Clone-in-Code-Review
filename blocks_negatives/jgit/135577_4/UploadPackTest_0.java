		assertThat(
				Arrays.asList(pckIn.readString(), pckIn.readString(),
						pckIn.readString()),
				hasItems("ls-refs", "fetch=ref-in-want shallow",
						"server-option"));
		assertTrue(PacketLineIn.isEnd(pckIn.readString()));
