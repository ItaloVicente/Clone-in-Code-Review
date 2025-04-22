	@Test
	public void testV2FetchMissingShallow() throws Exception {
		RevCommit one = remote.commit().message("1").create();
		RevCommit two = remote.commit().message("2").parent(one).create();
		RevCommit three = remote.commit().message("3").parent(two).create();
		remote.update("three"

		server.getConfig().setBoolean("uploadpack"
				true);

		ByteArrayInputStream recvStream = uploadPackV2("command=fetch\n"
				PacketLineIn.DELIM
				"want-ref refs/heads/three\n"
				"deepen 3"
				"shallow 0123012301230123012301230123012301230123"
				"shallow " + two.getName() + '\n'
				"done\n"
				PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
				is("shallow " + one.toObjectId().getName()));
		assertThat(pckIn.readString()
				is("unshallow " + two.toObjectId().getName()));
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
				is(three.toObjectId().getName() + " refs/heads/three"));
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		parsePack(recvStream);

		assertTrue(client.hasObject(one.toObjectId()));
		assertTrue(client.hasObject(two.toObjectId()));
		assertTrue(client.hasObject(three.toObjectId()));
	}

