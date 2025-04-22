	@Test
	public void testNotAdvertisedWantsV1Fetch() throws Exception {
		String commonInBlob = "abcdefghijklmnopqrstuvwxyz";

		RevBlob parentBlob = remote.blob(commonInBlob + "a");
		RevCommit parent = remote
				.commit(remote.tree(remote.file("foo"
		RevBlob childBlob = remote.blob(commonInBlob + "b");
		RevCommit child = remote
				.commit(remote.tree(remote.file("foo"
		remote.update("branch1"

		uploadPackV1("want " + child.toObjectId().getName() + "\n"
				PacketLineIn.end()
				"have " + parent.toObjectId().getName() + "\n"
				"done\n"

		assertEquals(0
	}

	@Test
	public void testNotAdvertisedWantsV1FetchRequestPolicyReachableCommit() throws Exception {
		String commonInBlob = "abcdefghijklmnopqrstuvwxyz";

		RevBlob parentBlob = remote.blob(commonInBlob + "a");
		RevCommit parent = remote
				.commit(remote.tree(remote.file("foo"
		RevBlob childBlob = remote.blob(commonInBlob + "b");
		RevCommit child = remote
				.commit(remote.tree(remote.file("foo"

		remote.update("branch1"

		uploadPackV1((UploadPack up) -> {up.setRequestPolicy(RequestPolicy.REACHABLE_COMMIT);}
				"want " + parent.toObjectId().getName() + "\n"
				PacketLineIn.end()
				"done\n"

		assertEquals(1
	}

	@Test
	public void testNotAdvertisedWantsV2FetchThinPack() throws Exception {
		String commonInBlob = "abcdefghijklmnopqrstuvwxyz";

		RevBlob parentBlob = remote.blob(commonInBlob + "a");
		RevCommit parent = remote
				.commit(remote.tree(remote.file("foo"
		RevBlob childBlob = remote.blob(commonInBlob + "b");
		RevCommit child = remote
				.commit(remote.tree(remote.file("foo"
		remote.update("branch1"

		ByteArrayInputStream recvStream = uploadPackV2("command=fetch\n"
				PacketLineIn.delimiter()
				"want " + child.toObjectId().getName() + "\n"
				"have " + parent.toObjectId().getName() + "\n"
				"done\n"
		PacketLineIn pckIn = new PacketLineIn(recvStream);

		assertThat(pckIn.readString()

		assertEquals(0
	}

	@Test
	public void testNotAdvertisedWantsV2FetchRequestPolicyReachableCommit() throws Exception {
		String commonInBlob = "abcdefghijklmnopqrstuvwxyz";

		RevBlob parentBlob = remote.blob(commonInBlob + "a");
		RevCommit parent = remote
				.commit(remote.tree(remote.file("foo"
		RevBlob childBlob = remote.blob(commonInBlob + "b");
		RevCommit child = remote
				.commit(remote.tree(remote.file("foo"

		remote.update("branch1"

		uploadPackV2((UploadPack up) -> {up.setRequestPolicy(RequestPolicy.REACHABLE_COMMIT);}
				"command=fetch\n"
				PacketLineIn.delimiter()
				"want " + parent.toObjectId().getName() + "\n"
				"done\n"

		assertEquals(1
	}

