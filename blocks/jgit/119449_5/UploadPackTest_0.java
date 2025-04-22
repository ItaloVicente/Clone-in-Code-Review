
	private void parsePack(ByteArrayInputStream recvStream) throws Exception {
		SideBandInputStream sb = new SideBandInputStream(
				recvStream
				new StringWriter()
		client.newObjectInserter().newPackParser(sb).parse(NullProgressMonitor.INSTANCE);
	}

	@Test
	public void testV2FetchServerDoesNotStopNegotiation() throws Exception {
		RevCommit fooParent = remote.commit().message("x").create();
		RevCommit fooChild = remote.commit().message("x").parent(fooParent).create();
		RevCommit barParent = remote.commit().message("y").create();
		RevCommit barChild = remote.commit().message("y").parent(barParent).create();

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + fooChild.toObjectId().getName() + "\n"
			"want " + barChild.toObjectId().getName() + "\n"
			"have " + fooParent.toObjectId().getName() + "\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
	}

	@Test
	public void testV2FetchServerStopsNegotiation() throws Exception {
		RevCommit fooParent = remote.commit().message("x").create();
		RevCommit fooChild = remote.commit().message("x").parent(fooParent).create();
		RevCommit barParent = remote.commit().message("y").create();
		RevCommit barChild = remote.commit().message("y").parent(barParent).create();

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + fooChild.toObjectId().getName() + "\n"
			"want " + barChild.toObjectId().getName() + "\n"
			"have " + fooParent.toObjectId().getName() + "\n"
			"have " + barParent.toObjectId().getName() + "\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(
			Arrays.asList(pckIn.readString()
			hasItems(
				"ACK " + fooParent.toObjectId().getName()
				"ACK " + barParent.toObjectId().getName()));
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertFalse(client.hasObject(fooParent.toObjectId()));
		assertTrue(client.hasObject(fooChild.toObjectId()));
		assertFalse(client.hasObject(barParent.toObjectId()));
		assertTrue(client.hasObject(barChild.toObjectId()));
	}

	@Test
	public void testV2FetchClientStopsNegotiation() throws Exception {
		RevCommit fooParent = remote.commit().message("x").create();
		RevCommit fooChild = remote.commit().message("x").parent(fooParent).create();
		RevCommit barParent = remote.commit().message("y").create();
		RevCommit barChild = remote.commit().message("y").parent(barParent).create();

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + fooChild.toObjectId().getName() + "\n"
			"want " + barChild.toObjectId().getName() + "\n"
			"have " + fooParent.toObjectId().getName() + "\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertFalse(client.hasObject(fooParent.toObjectId()));
		assertTrue(client.hasObject(fooChild.toObjectId()));
		assertTrue(client.hasObject(barParent.toObjectId()));
		assertTrue(client.hasObject(barChild.toObjectId()));
	}
