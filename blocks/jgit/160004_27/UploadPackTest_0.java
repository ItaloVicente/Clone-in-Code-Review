	@Test
	public void testUploadNewBytes() throws Exception {
		String commonInBlob = "abcdefghijklmnopqrstuvwx";

		RevBlob parentBlob = remote.blob(commonInBlob + "a");
		RevCommit parent = remote.commit(remote.tree(remote.file("foo"
		RevBlob childBlob = remote.blob(commonInBlob + "b");
		RevCommit child = remote.commit(remote.tree(remote.file("foo"
		remote.update("branch1"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.delimiter()
			"want " + child.toObjectId().getName() + "\n"
			"ofs-delta\n"
			"done\n"
				PacketLineIn.end());
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		ReceivedPackStatistics receivedStats = parsePack(recvStream);
		assertTrue(receivedStats.getNumBytesDuplicated() == 0);
		assertTrue(receivedStats.getNumObjectsDuplicated() == 0);
	}

	@Test
	public void testUploadRedundantBytes() throws Exception {
		String commonInBlob = "abcdefghijklmnopqrstuvwxyz";

		RevBlob parentBlob = remote.blob(commonInBlob + "a");
		RevCommit parent = remote
				.commit(remote.tree(remote.file("foo"
		RevBlob childBlob = remote.blob(commonInBlob + "b");
		RevCommit child = remote
				.commit(remote.tree(remote.file("foo"
		remote.update("branch1"

		TestRepository<InMemoryRepository> local = new TestRepository<>(client);
		RevBlob localParentBlob = local.blob(commonInBlob + "a");
		RevCommit localParent = local
				.commit(local.tree(local.file("foo"
		RevBlob localChildBlob = local.blob(commonInBlob + "b");
		RevCommit localChild = local.commit(
				local.tree(local.file("foo"
		local.update("branch1"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.delimiter()
			"want " + child.toObjectId().getName() + "\n"
			"ofs-delta\n"
				PacketLineIn.end());
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		ReceivedPackStatistics receivedStats = parsePack(recvStream);

		long sizeOfHeader = 12;
		long sizeOfTrailer = 20;
		long expectedSize = receivedStats.getNumBytesRead() - sizeOfHeader
				- sizeOfTrailer;
		assertTrue(receivedStats.getNumBytesDuplicated() == expectedSize);
		assertTrue(receivedStats.getNumObjectsDuplicated() == 6);
	}

