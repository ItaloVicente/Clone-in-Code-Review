	@Test
	public void testV2FetchShallowSince() throws Exception {
		PersonIdent person = new PersonIdent(remote.getRepository());

		RevCommit beyondBoundary = remote.commit()
			.committer(new PersonIdent(person
		RevCommit boundary = remote.commit().parent(beyondBoundary)
			.committer(new PersonIdent(person
		RevCommit tooOld = remote.commit()
			.committer(new PersonIdent(person
		RevCommit merge = remote.commit().parent(boundary).parent(tooOld)
			.committer(new PersonIdent(person

		remote.update("branch1"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"shallow " + boundary.toObjectId().getName() + "\n"
			"deepen-since 1510000\n"
			"want " + merge.toObjectId().getName() + "\n"
			"have " + boundary.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()

		assertThat(pckIn.readString()

		assertThat(pckIn.readString()

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		parsePack(recvStream);

		assertFalse(client.hasObject(tooOld.toObjectId()));

		assertFalse(client.hasObject(boundary.toObjectId()));

		assertTrue(client.hasObject(beyondBoundary.toObjectId()));
		assertTrue(client.hasObject(merge.toObjectId()));
	}

