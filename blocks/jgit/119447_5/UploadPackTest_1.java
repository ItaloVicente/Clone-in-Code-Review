
	private static ByteArrayInputStream send(String... lines) throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PacketLineOut pckOut = new PacketLineOut(os);
		for (String line : lines) {
			if (line == PacketLineIn.END) {
				pckOut.end();
			} else if (line == PacketLineIn.DELIM) {
				pckOut.writeDelim();
			} else {
				pckOut.writeString(line);
			}
		}
		byte[] a = os.toByteArray();
		return new ByteArrayInputStream(a);
	}

	private ByteArrayInputStream uploadPackV2(String... inputLines) throws Exception {
		ByteArrayOutputStream send = new ByteArrayOutputStream();
		PacketLineOut pckOut = new PacketLineOut(send);
		for (String line : inputLines) {
			if (line == PacketLineIn.END) {
				pckOut.end();
			} else if (line == PacketLineIn.DELIM) {
				pckOut.writeDelim();
			} else {
				pckOut.writeString(line);
			}
		}

		UploadPack up = new UploadPack(server);
		up.setUseProtocolV2(true);

		ByteArrayOutputStream recv = new ByteArrayOutputStream();
		up.upload(new ByteArrayInputStream(send.toByteArray())

		ByteArrayInputStream recvStream = new ByteArrayInputStream(recv.toByteArray());
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertTrue(pckIn.readString() == PacketLineIn.END);
		return recvStream;
	}

	@Test
	public void testV2EmptyRequest() throws Exception {
		ByteArrayInputStream recvStream = uploadPackV2(PacketLineIn.END);
		assertThat(recvStream.available()
	}

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
