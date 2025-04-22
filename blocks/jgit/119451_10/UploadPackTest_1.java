	@Test
	public void testV2FetchNoProgress() throws Exception {
		RevCommit commit = remote.commit().message("x").create();
		remote.update("branch1"

		StringWriter sw = new StringWriter();
		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + commit.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		parsePack(recvStream
		assertFalse(sw.toString().isEmpty());

		sw = new StringWriter();
		recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + commit.toObjectId().getName() + "\n"
			"no-progress\n"
			"done\n"
			PacketLineIn.END);
		pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		parsePack(recvStream
		assertTrue(sw.toString().isEmpty());
	}

