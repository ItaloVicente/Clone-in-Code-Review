	@Test
	public void testV2LsRefs() throws Exception {
		RevCommit tip = remote.commit().message("message").create();
		remote.update("master"
		server.updateRef("HEAD").link("refs/heads/master");
		RevTag tag = remote.tag("tag"
		remote.update("refs/tags/tag"

		ByteArrayInputStream recvStream = uploadPackV2("command=ls-refs\n"
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertTrue(pckIn.readString() == PacketLineIn.END);
	}

	@Test
	public void testV2LsRefsSymrefs() throws Exception {
		RevCommit tip = remote.commit().message("message").create();
		remote.update("master"
		server.updateRef("HEAD").link("refs/heads/master");
		RevTag tag = remote.tag("tag"
		remote.update("refs/tags/tag"

		ByteArrayInputStream recvStream = uploadPackV2("command=ls-refs\n"
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertTrue(pckIn.readString() == PacketLineIn.END);
	}

	@Test
	public void testV2LsRefsPeel() throws Exception {
		RevCommit tip = remote.commit().message("message").create();
		remote.update("master"
		server.updateRef("HEAD").link("refs/heads/master");
		RevTag tag = remote.tag("tag"
		remote.update("refs/tags/tag"

		ByteArrayInputStream recvStream = uploadPackV2("command=ls-refs\n"
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(
			pckIn.readString()
			is(tag.toObjectId().getName() + " refs/tags/tag peeled:"
				+ tip.toObjectId().getName()));
		assertTrue(pckIn.readString() == PacketLineIn.END);
	}

	@Test
	public void testV2LsRefsMultipleCommands() throws Exception {
		RevCommit tip = remote.commit().message("message").create();
		remote.update("master"
		server.updateRef("HEAD").link("refs/heads/master");
		RevTag tag = remote.tag("tag"
		remote.update("refs/tags/tag"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=ls-refs\n"
			"command=ls-refs\n"
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(
			pckIn.readString()
			is(tag.toObjectId().getName() + " refs/tags/tag peeled:"
				+ tip.toObjectId().getName()));
		assertTrue(pckIn.readString() == PacketLineIn.END);
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertTrue(pckIn.readString() == PacketLineIn.END);
	}
