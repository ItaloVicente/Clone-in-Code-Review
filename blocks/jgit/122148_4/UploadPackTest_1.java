	@Test
	public void testV2FetchFilter() throws Exception {
		RevBlob big = remote.blob("foobar");
		RevBlob small = remote.blob("fooba");
		RevTree tree = remote.tree(remote.file("1"
				remote.file("2"
		RevCommit commit = remote.commit(tree);
		remote.update("master"

		server.getConfig().setBoolean("uploadpack"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + commit.toObjectId().getName() + "\n"
			"filter blob:limit=5\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		parsePack(recvStream);

		assertFalse(client.hasObject(big.toObjectId()));
		assertTrue(client.hasObject(small.toObjectId()));
	}

	@Test
	public void testV2FetchFilterWhenNotAllowed() throws Exception {
		RevCommit commit = remote.commit().message("0").create();
		remote.update("master"

		server.getConfig().setBoolean("uploadpack"

		thrown.expect(PackProtocolException.class);
		thrown.expectMessage("unexpected filter blob:limit=5");
		uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + commit.toObjectId().getName() + "\n"
			"filter blob:limit=5\n"
			"done\n"
			PacketLineIn.END);
	}

