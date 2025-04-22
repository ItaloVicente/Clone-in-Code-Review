	@Test
	public void testV2FetchMotd() throws Exception {
		RevCommit commit = remote.commit().message("x").create();
		remote.update("master"

		server.getConfig().setBoolean("uploadpack"
		server.getConfig().setString("uploadpack"

		ByteArrayInputStream recvStream = uploadPackV2("command=fetch\n"
				PacketLineIn.DELIM
				"want " + commit.getName() + "\n"
				"sideband-all\n"
				"done\n"
				PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		parsePack(recvStream);
	}

