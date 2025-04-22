	@Test
	public void testV2FetchWantRefIfNotAllowed() throws Exception {
		RevCommit one = remote.commit().message("1").create();
		remote.update("one"

		thrown.expect(PackProtocolException.class);
		thrown.expectMessage("unexpected want-ref refs/heads/one");
		uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want-ref refs/heads/one\n"
			"done\n"
			PacketLineIn.END);
	}

	@Test
	public void testV2FetchWantRef() throws Exception {
		RevCommit one = remote.commit().message("1").create();
		RevCommit two = remote.commit().message("2").create();
		RevCommit three = remote.commit().message("3").create();
		remote.update("one"
		remote.update("two"
		remote.update("three"

		server.getConfig().setBoolean("uploadpack"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want-ref refs/heads/one\n"
			"want-ref refs/heads/two\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		assertThat(
				Arrays.asList(pckIn.readString()
				hasItems(
					one.toObjectId().getName() + " refs/heads/one"
					two.toObjectId().getName() + " refs/heads/two"));
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		parsePack(recvStream);

		assertTrue(client.hasObject(one.toObjectId()));
		assertTrue(client.hasObject(two.toObjectId()));
		assertFalse(client.hasObject(three.toObjectId()));
	}

	@Test
	public void testV2FetchBadWantRef() throws Exception {
		RevCommit one = remote.commit().message("1").create();
		remote.update("one"

		server.getConfig().setBoolean("uploadpack"

		thrown.expect(PackProtocolException.class);
		thrown.expectMessage("Invalid ref name: refs/heads/nonExistentRef");
		uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want-ref refs/heads/one\n"
			"want-ref refs/heads/nonExistentRef\n"
			"done\n"
			PacketLineIn.END);
	}

	@Test
	public void testV2FetchMixedWantRef() throws Exception {
		RevCommit one = remote.commit().message("1").create();
		RevCommit two = remote.commit().message("2").create();
		RevCommit three = remote.commit().message("3").create();
		remote.update("one"
		remote.update("two"
		remote.update("three"

		server.getConfig().setBoolean("uploadpack"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want-ref refs/heads/one\n"
			"want " + two.toObjectId().getName()
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		assertThat(
				pckIn.readString()
				is(one.toObjectId().getName() + " refs/heads/one"));
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		parsePack(recvStream);

		assertTrue(client.hasObject(one.toObjectId()));
		assertTrue(client.hasObject(two.toObjectId()));
		assertFalse(client.hasObject(three.toObjectId()));
	}

	@Test
	public void testV2FetchWantRefWeAlreadyHave() throws Exception {
		RevCommit one = remote.commit().message("1").create();
		remote.update("one"

		server.getConfig().setBoolean("uploadpack"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want-ref refs/heads/one\n"
			"have " + one.toObjectId().getName()
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(
				pckIn.readString()
				is(one.toObjectId().getName() + " refs/heads/one"));
		assertThat(pckIn.readString()

		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertFalse(client.hasObject(one.toObjectId()));
	}

	@Test
	public void testV2FetchWantRefAndDeepen() throws Exception {
		RevCommit parent = remote.commit().message("parent").create();
		RevCommit child = remote.commit().message("x").parent(parent).create();
		remote.update("branch1"

		server.getConfig().setBoolean("uploadpack"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want-ref refs/heads/branch1\n"
			"deepen 1\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertTrue(client.hasObject(child.toObjectId()));
		assertFalse(client.hasObject(parent.toObjectId()));
	}

