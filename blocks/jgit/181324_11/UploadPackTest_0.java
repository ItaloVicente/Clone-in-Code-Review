	@Test
	public void testV2FetchServerNegotiationUsingWaitForDoneWhenServerAcksOne()
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

		server.getConfig().setBoolean("uploadpack"
				true);
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
	public void testV2FetchServerNegotiationUsingWaitForDoneWhenServerAcksAll()
			throws Exception {
		String commonInBlob = "abcdefghijklmnopqrstuvwxyz";

		RevBlob parentBlob = remote.blob(commonInBlob + "a");
		RevCommit parent = remote
				.commit(remote.tree(remote.file("foo"
		RevBlob childBlob = remote.blob(commonInBlob + "b");
		RevCommit child = remote
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

		server.getConfig().setBoolean("uploadpack"
				true);
		ByteArrayInputStream recvStream = uploadPackV2("command=fetch\n"
				PacketLineIn.delimiter()
				"have " + localParent.toObjectId().getName() + "\n"
				"have " + localChild.toObjectId().getName() + "\n"
				PacketLineIn.end());
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		assertThat(Arrays.asList(pckIn.readString()
				hasItems("ACK " + parent.toObjectId().getName()
						"ACK " + child.toObjectId().getName()));
		assertTrue(PacketLineIn.isEnd(pckIn.readString()));
	}

	@Test
	public void testV2FetchServerNegotiationUsingWaitForDoneWhenServerAcksNone()
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

		server.getConfig().setBoolean("uploadpack"
				true);
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

	@Test
	public void testV2FetchServerNegotiationWhenWaitForDoneIsDisabled()
			throws Exception {
		server.getConfig().setBoolean("uploadpack"
				false);
		assertThrows(UploadPackInternalServerErrorException.class
				() -> uploadPackV2("command=fetch\n"
				PacketLineIn.delimiter()
						PacketLineIn.end()));
	}

