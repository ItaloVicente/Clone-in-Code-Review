	@Test
	public void testV2FetchDeepenNot() throws Exception {
		RevCommit one = remote.commit().message("one").create();
		RevCommit two = remote.commit().message("two").parent(one).create();
		RevCommit three = remote.commit().message("three").parent(two).create();
		RevCommit side = remote.commit().message("side").parent(one).create();
		RevCommit merge = remote.commit().message("merge")
			.parent(three).parent(side).create();

		remote.update("branch1"
		remote.update("side"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"shallow " + three.toObjectId().getName() + "\n"
			"deepen-not side\n"
			"want " + merge.toObjectId().getName() + "\n"
			"have " + three.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()

		assertThat(
			Arrays.asList(pckIn.readString()
			hasItems(
				"shallow " + merge.toObjectId().getName()
				"shallow " + two.toObjectId().getName()));

		assertThat(pckIn.readString()

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		parsePack(recvStream);

		assertFalse(client.hasObject(side.toObjectId()));
		assertFalse(client.hasObject(one.toObjectId()));

		assertFalse(client.hasObject(three.toObjectId()));

		assertTrue(client.hasObject(merge.toObjectId()));
		assertTrue(client.hasObject(two.toObjectId()));
	}

	@Test
	public void testV2FetchDeepenNot_excludeDescendantOfWant() throws Exception {
		RevCommit one = remote.commit().message("one").create();
		RevCommit two = remote.commit().message("two").parent(one).create();
		RevCommit three = remote.commit().message("three").parent(two).create();
		RevCommit four = remote.commit().message("four").parent(three).create();

		remote.update("two"
		remote.update("four"

		thrown.expect(PackProtocolException.class);
		thrown.expectMessage("No commits selected for shallow request");
		uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"deepen-not four\n"
			"want " + two.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
	}

	@Test
	public void testV2FetchDeepenNot_supportAnnotatedTags() throws Exception {
		RevCommit one = remote.commit().message("one").create();
		RevCommit two = remote.commit().message("two").parent(one).create();
		RevCommit three = remote.commit().message("three").parent(two).create();
		RevCommit four = remote.commit().message("four").parent(three).create();
		RevTag twoTag = remote.tag("twotag"

		remote.update("refs/tags/twotag"
		remote.update("four"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"deepen-not twotag\n"
			"want " + four.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertFalse(client.hasObject(one.toObjectId()));
		assertFalse(client.hasObject(two.toObjectId()));
		assertTrue(client.hasObject(three.toObjectId()));
		assertTrue(client.hasObject(four.toObjectId()));
	}

