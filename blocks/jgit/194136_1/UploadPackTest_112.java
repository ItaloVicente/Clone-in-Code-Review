	@Test
	public void testV2FetchServerStopsNegotiationForRefWithoutParents()
			throws Exception {
		RevCommit fooCommit = remote.commit().message("x").create();
		RevCommit barCommit = remote.commit().message("y").create();
		remote.update("refs/changes/01/1/1"
		remote.update("refs/changes/02/2/1"

		ByteArrayInputStream recvStream = uploadPackV2("command=fetch\n"
				PacketLineIn.delimiter()
				"want " + fooCommit.toObjectId().getName() + "\n"
				"have " + barCommit.toObjectId().getName() + "\n"
				PacketLineIn.end());
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
				is("ACK " + barCommit.toObjectId().getName()));
		assertThat(pckIn.readString()
		assertTrue(PacketLineIn.isDelimiter(pckIn.readString()));
		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertTrue(client.getObjectDatabase().has(fooCommit.toObjectId()));
	}

	@Test
	public void testV2FetchServerDoesNotStopNegotiationWhenOneRefWithoutParentAndOtherWithParents()
			throws Exception {
		RevCommit fooCommit = remote.commit().message("x").create();
		RevCommit barParent = remote.commit().message("y").create();
		RevCommit barChild = remote.commit().message("y").parent(barParent)
				.create();
		RevCommit fooBarParent = remote.commit().message("z").create();
		RevCommit fooBarChild = remote.commit().message("y")
				.parent(fooBarParent)
				.create();
		remote.update("refs/changes/01/1/1"
		remote.update("refs/changes/02/2/1"
		remote.update("refs/changes/03/3/1"

		ByteArrayInputStream recvStream = uploadPackV2("command=fetch\n"
				PacketLineIn.delimiter()
				"want " + fooCommit.toObjectId().getName() + "\n"
				"want " + barChild.toObjectId().getName() + "\n"
				"want " + fooBarChild.toObjectId().getName() + "\n"
				"have " + fooBarParent.toObjectId().getName() + "\n"
				PacketLineIn.end());
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
				is("ACK " + fooBarParent.toObjectId().getName()));
		assertTrue(PacketLineIn.isEnd(pckIn.readString()));
	}

