	@Test
	public void testV2FetchPackfileUris() throws Exception {
		RevCommit commit = remote.commit().message("x").create();
		remote.update("master"
		generateBitmaps(server);

		RevCommit commit2 = remote.commit().message("x").parent(commit).create();
		remote.update("master"

		server.getConfig().setBoolean("uploadpack"

		ByteArrayInputStream recvStream = uploadPackV2(
			(UploadPack up) -> {
				up.setCachedPackUriProvider(new CachedPackUriProvider() {
					@Override
					public PackInfo getInfo(CachedPack pack
							Collection<String> protocolsSupported)
							throws IOException {
						assertThat(protocolsSupported
						if (!protocolsSupported.contains("https"))
							return null;
						return new PackInfo("myhash"
					}

				});
			}
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + commit2.getName() + "\n"
			"sideband-all\n"
			"packfile-uris https\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		String s;
		for (s = pckIn.readString(); s.startsWith("\002"); s = pckIn.readString()) {
		}
		assertThat(s
		assertThat(pckIn.readString()
		assertTrue(PacketLineIn.isDelimiter(pckIn.readString()));
		assertThat(pckIn.readString()
		parsePack(recvStream);

		assertFalse(client.getObjectDatabase().has(commit.toObjectId()));
		assertTrue(client.getObjectDatabase().has(commit2.toObjectId()));
	}

