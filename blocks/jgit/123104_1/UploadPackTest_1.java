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

	@Test
	public void testV2FetchOfsDelta() throws Exception {
		String commonInBlob = "abcdefghijklmnopqrstuvwxyz";

		RevBlob parentBlob = remote.blob(commonInBlob + "a");
		RevCommit parent = remote.commit(remote.tree(remote.file("foo"
		RevBlob childBlob = remote.blob(commonInBlob + "b");
		RevCommit child = remote.commit(remote.tree(remote.file("foo"
		remote.update("branch1"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + child.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		ReceivedPackStatistics stats = parsePack(recvStream);
		assertTrue(stats.getNumOfsDelta() == 0);

		recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + child.toObjectId().getName() + "\n"
			"ofs-delta\n"
			"done\n"
			PacketLineIn.END);
		pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		stats = parsePack(recvStream);
		assertTrue(stats.getNumOfsDelta() != 0);
	}

