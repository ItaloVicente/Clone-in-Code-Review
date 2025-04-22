
	@Test
	public void testV2LsRefsRefPrefix() throws Exception {
		RevCommit tip = remote.commit().message("message").create();
		remote.update("master"
		remote.update("other"
		remote.update("yetAnother"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=ls-refs\n"
			PacketLineIn.DELIM
			"ref-prefix refs/heads/maste"
			"ref-prefix refs/heads/other"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertTrue(pckIn.readString() == PacketLineIn.END);
	}

	@Test
	public void testV2LsRefsRefPrefixNoSlash() throws Exception {
		RevCommit tip = remote.commit().message("message").create();
		remote.update("master"
		remote.update("other"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=ls-refs\n"
			PacketLineIn.DELIM
			"ref-prefix refs/heads/maste"
			"ref-prefix r"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertTrue(pckIn.readString() == PacketLineIn.END);
	}
