	@Test
	public void testGetPeerAgentProtocolV0() throws Exception {
		RevCommit one = remote.commit().message("1").create();
		remote.update("one"

		UploadPack up = new UploadPack(server);
		ByteArrayInputStream send = linesAsInputStream(
				"want " + one.getName() + " agent=JGit-test/1.2.3\n"
				PacketLineIn.END
				"have 11cedf1b796d44207da702f7d420684022fc0f09\n"

		ByteArrayOutputStream recv = new ByteArrayOutputStream();
		up.upload(send

		assertEquals(up.getPeerUserAgent()
	}

	@Test
	public void testGetPeerAgentProtocolV2() throws Exception {
		server.getConfig().setString("protocol"

		RevCommit one = remote.commit().message("1").create();
		remote.update("one"

		UploadPack up = new UploadPack(server);
		up.setExtraParameters(Sets.of("version=2"));

		ByteArrayInputStream send = linesAsInputStream(
				"command=fetch\n"
				PacketLineIn.DELIM
				"have 11cedf1b796d44207da702f7d420684022fc0f09\n"
				PacketLineIn.END);

		ByteArrayOutputStream recv = new ByteArrayOutputStream();
		up.upload(send

		assertEquals(up.getPeerUserAgent()
	}

