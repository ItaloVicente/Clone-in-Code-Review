	@Test
	public void testGetSessionIDValueProtocolV2() throws Exception {
		server.getConfig().setString(ConfigConstants.CONFIG_PROTOCOL_SECTION
				null
				TransferConfig.ProtocolVersion.V2.version());

		RevCommit one = remote.commit().message("1").create();
		remote.update("one"

		UploadPack up = new UploadPack(server);
		up.setExtraParameters(Sets.of("version=2"));

		ByteArrayInputStream send = linesAsInputStream("command=fetch\n"
				"agent=JGit-test/1.2.4\n"
				PacketLineIn.delimiter()
				"have 11cedf1b796d44207da702f7d420684022fc0f09\n"
				PacketLineIn.end());

		ByteArrayOutputStream recv = new ByteArrayOutputStream();
		up.upload(send

		assertEquals(up.getClientSID()
	}

