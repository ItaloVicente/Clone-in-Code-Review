	@Test
	public void testV2FetchSidebandAllNoPackfile() throws Exception {
		RevCommit fooParent = remote.commit().message("x").create();
		RevCommit fooChild = remote.commit().message("x").parent(fooParent).create();
		RevCommit barParent = remote.commit().message("y").create();
		RevCommit barChild = remote.commit().message("y").parent(barParent).create();
		remote.update("branch1"
		remote.update("branch2"

		server.getConfig().setBoolean("uploadpack"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"sideband-all\n"
			"want " + fooChild.toObjectId().getName() + "\n"
			"want " + barChild.toObjectId().getName() + "\n"
			"have " + fooParent.toObjectId().getName() + "\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertTrue(PacketLineIn.isEnd(pckIn.readString()));
	}

	@Test
	public void testV2FetchSidebandAllPackfile() throws Exception {
		RevCommit commit = remote.commit().message("x").create();
		remote.update("master"

		server.getConfig().setBoolean("uploadpack"

		ByteArrayInputStream recvStream = uploadPackV2("command=fetch\n"
				PacketLineIn.DELIM
				"want " + commit.getName() + "\n"
				"sideband-all\n"
				"done\n"
				PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		parsePack(recvStream);
	}

