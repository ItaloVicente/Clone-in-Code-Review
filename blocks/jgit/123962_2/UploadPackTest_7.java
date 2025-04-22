	@Test
	public void testV2FetchShallow() throws Exception {
		RevCommit commonParent = remote.commit().message("parent").create();
		RevCommit fooChild = remote.commit().message("x").parent(commonParent).create();
		RevCommit barChild = remote.commit().message("y").parent(commonParent).create();
		remote.update("branch1"

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
		remote.update("branch1"

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
		remote.update("branch1"

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
	}

	@Test
	public void testV2FetchUnrecognizedArgument() throws Exception {
		thrown.expect(PackProtocolException.class);
		thrown.expectMessage("unexpected invalid-argument");
		uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"invalid-argument\n"
			PacketLineIn.END);
	}

	@Test
	public void testV2FetchFilter() throws Exception {
		RevBlob big = remote.blob("foobar");
		RevBlob small = remote.blob("fooba");
		RevTree tree = remote.tree(remote.file("1"
				remote.file("2"
		RevCommit commit = remote.commit(tree);
		remote.update("master"

		server.getConfig().setBoolean("uploadpack"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + commit.toObjectId().getName() + "\n"
			"filter blob:limit=5\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		parsePack(recvStream);

		assertFalse(client.hasObject(big.toObjectId()));
		assertTrue(client.hasObject(small.toObjectId()));
	}

	@Test
	public void testV2FetchFilterWhenNotAllowed() throws Exception {
		RevCommit commit = remote.commit().message("0").create();
		remote.update("master"

		server.getConfig().setBoolean("uploadpack"

		thrown.expect(PackProtocolException.class);
		thrown.expectMessage("unexpected filter blob:limit=5");
		uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + commit.toObjectId().getName() + "\n"
			"filter blob:limit=5\n"
			"done\n"
			PacketLineIn.END);
	}

