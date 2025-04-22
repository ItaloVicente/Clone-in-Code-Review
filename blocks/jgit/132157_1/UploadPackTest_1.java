	@Test
	public void testV2FetchDeepenNot_excludedParentWithMultipleChildren() throws Exception {
		PersonIdent person = new PersonIdent(remote.getRepository());

		RevCommit base = remote.commit()
			.committer(new PersonIdent(person
		RevCommit child1 = remote.commit().parent(base)
			.committer(new PersonIdent(person
		RevCommit child2 = remote.commit().parent(base)
			.committer(new PersonIdent(person

		remote.update("base"
		remote.update("branch1"
		remote.update("branch2"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"deepen-not base\n"
			"want " + child1.toObjectId().getName() + "\n"
			"want " + child2.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()

		assertThat(
			Arrays.asList(pckIn.readString()
			hasItems(
				"shallow " + child1.toObjectId().getName()
				"shallow " + child2.toObjectId().getName()));

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		parsePack(recvStream);

		assertFalse(client.hasObject(base.toObjectId()));
		assertTrue(client.hasObject(child1.toObjectId()));
		assertTrue(client.hasObject(child2.toObjectId()));
	}

