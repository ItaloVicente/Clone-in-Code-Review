	@Test
	public void testV2FetchWithoutWaitForDoneReceivesPackfile()
			throws Exception {
		String commonInBlob = "abcdefghijklmnopqrstuvwxyz";

		RevBlob parentBlob = remote.blob(commonInBlob + "a");
		RevCommit parent = remote
				.commit(remote.tree(remote.file("foo"
		remote.update("branch1"

		RevCommit localParent = null;
		RevCommit localChild = null;
		try (TestRepository<InMemoryRepository> local = new TestRepository<>(
				client)) {
			RevBlob localParentBlob = local.blob(commonInBlob + "a");
			localParent = local
					.commit(local.tree(local.file("foo"
			RevBlob localChildBlob = local.blob(commonInBlob + "b");
			localChild = local.commit(
					local.tree(local.file("foo"
			local.update("branch1"
		}

		ByteArrayInputStream recvStream = uploadPackV2("command=fetch\n"
				PacketLineIn.delimiter()
				"have " + localParent.toObjectId().getName() + "\n"
				"have " + localChild.toObjectId().getName() + "\n"
				PacketLineIn.end());
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		assertThat(Arrays.asList(pckIn.readString())
				hasItems("ACK " + parent.toObjectId().getName()));
		assertThat(pckIn.readString()
		assertTrue(PacketLineIn.isDelimiter(pckIn.readString()));
		assertThat(pckIn.readString()
		parsePack(recvStream);
	}

	@Test
	public void testV2FetchWithWaitForDoneOnlyDoesNegotiation()
			throws Exception {
		String commonInBlob = "abcdefghijklmnopqrstuvwxyz";

		RevBlob parentBlob = remote.blob(commonInBlob + "a");
		RevCommit parent = remote
				.commit(remote.tree(remote.file("foo"
		remote.update("branch1"

		RevCommit localParent = null;
		RevCommit localChild = null;
		try (TestRepository<InMemoryRepository> local = new TestRepository<>(
				client)) {
			RevBlob localParentBlob = local.blob(commonInBlob + "a");
			localParent = local
					.commit(local.tree(local.file("foo"
			RevBlob localChildBlob = local.blob(commonInBlob + "b");
			localChild = local.commit(
					local.tree(local.file("foo"
			local.update("branch1"
		}

		ByteArrayInputStream recvStream = uploadPackV2("command=fetch\n"
				PacketLineIn.delimiter()
				"have " + localParent.toObjectId().getName() + "\n"
				"have " + localChild.toObjectId().getName() + "\n"
				PacketLineIn.end());
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		assertThat(Arrays.asList(pckIn.readString())
				hasItems("ACK " + parent.toObjectId().getName()));
		assertTrue(PacketLineIn.isEnd(pckIn.readString()));
	}

	@Test
	public void testV2FetchWithWaitForDoneOnlyDoesNegotiationAndNothingToAck()
			throws Exception {
		String commonInBlob = "abcdefghijklmnopqrstuvwxyz";

		RevCommit localParent = null;
		RevCommit localChild = null;
		try (TestRepository<InMemoryRepository> local = new TestRepository<>(
				client)) {
			RevBlob localParentBlob = local.blob(commonInBlob + "a");
			localParent = local
					.commit(local.tree(local.file("foo"
			RevBlob localChildBlob = local.blob(commonInBlob + "b");
			localChild = local.commit(
					local.tree(local.file("foo"
			local.update("branch1"
		}

		ByteArrayInputStream recvStream = uploadPackV2("command=fetch\n"
				PacketLineIn.delimiter()
				"have " + localParent.toObjectId().getName() + "\n"
				"have " + localChild.toObjectId().getName() + "\n"
				PacketLineIn.end());
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertTrue(PacketLineIn.isEnd(pckIn.readString()));
	}

