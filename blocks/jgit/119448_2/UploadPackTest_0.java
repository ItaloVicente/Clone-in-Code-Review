
	@Test
	public void testV2LsRefsRefPrefix() throws Exception {
		RevCommit tip = remote.commit().message("message").create();
		remote.update("maste"
		remote.update("master"
		remote.update("other"

		ByteArrayInputStream recvStream = uploadPackV2("command=ls-refs\n"
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertTrue(pckIn.readString() == PacketLineIn.END);
	}
