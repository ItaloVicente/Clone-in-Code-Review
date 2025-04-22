		try (UploadPack up = new UploadPack(server)) {
			ByteArrayInputStream send = linesAsInputStream(
					"want " + one.getName() + " agent=JGit-test/1.2.3"
							+ " session-id=client-session-id\n"
					PacketLineIn.end()
					"have 11cedf1b796d44207da702f7d420684022fc0f09\n"
					"done\n");
