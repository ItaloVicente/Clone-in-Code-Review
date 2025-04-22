	@Test
	public void testV2FetchIncludeTag() throws Exception {
		RevCommit commit = remote.commit().message("x").create();
		RevTag tag = remote.tag("tag"
		remote.update("branch1"
		remote.update("refs/tags/tag"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + commit.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertFalse(client.hasObject(tag.toObjectId()));

		recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + commit.toObjectId().getName() + "\n"
			"include-tag\n"
			"done\n"
			PacketLineIn.END);
		pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertTrue(client.hasObject(tag.toObjectId()));
	}

