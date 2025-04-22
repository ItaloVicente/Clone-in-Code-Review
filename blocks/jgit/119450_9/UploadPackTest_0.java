	@Test
	public void testV2FetchThinPack() throws Exception {
		String commonInBlob = "abcdefghijklmnopqrstuvwxyz";

		RevBlob parentBlob = remote.blob(commonInBlob + "a");
		RevCommit parent = remote.commit(remote.tree(remote.file("foo"
		RevBlob childBlob = remote.blob(commonInBlob + "b");
		RevCommit child = remote.commit(remote.tree(remote.file("foo"
		remote.update("branch1"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + child.toObjectId().getName() + "\n"
			"have " + parent.toObjectId().getName() + "\n"
			"thin-pack\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()

		thrown.expect(IOException.class);
		thrown.expectMessage("pack has unresolved deltas");
		parsePack(recvStream);
	}

