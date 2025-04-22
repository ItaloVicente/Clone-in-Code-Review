
	@Test
	public void testV2FetchShallow() throws Exception {
		RevCommit commonParent = remote.commit().message("parent").create();
		RevCommit fooChild = remote.commit().message("x").parent(commonParent).create();
		RevCommit barChild = remote.commit().message("y").parent(commonParent).create();

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + barChild.toObjectId().getName() + "\n"
			"have " + fooChild.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertTrue(client.hasObject(barChild.toObjectId()));
		assertFalse(client.hasObject(commonParent.toObjectId()));

		recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + barChild.toObjectId().getName() + "\n"
			"have " + fooChild.toObjectId().getName() + "\n"
			"shallow " + fooChild.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertTrue(client.hasObject(commonParent.toObjectId()));
	}

	@Test
	public void testV2FetchDeepenAndDone() throws Exception {
		RevCommit parent = remote.commit().message("parent").create();
		RevCommit child = remote.commit().message("x").parent(parent).create();

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + child.toObjectId().getName() + "\n"
			"deepen 1\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertTrue(client.hasObject(child.toObjectId()));
		assertFalse(client.hasObject(parent.toObjectId()));

		recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + child.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertTrue(client.hasObject(parent.toObjectId()));
	}

	@Test
	public void testV2FetchDeepenWithoutDone() throws Exception {
		RevCommit parent = remote.commit().message("parent").create();
		RevCommit child = remote.commit().message("x").parent(parent).create();

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + child.toObjectId().getName() + "\n"
			"deepen 1\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
	}
