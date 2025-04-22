	@Test
	public void testReachabilityCheckDurationV2FetchThinPack() throws Exception {
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

		assertTrue(stats.getReachabilityCheckDuration() >= 0);
	}

	@Test
	public void testReachabilityCheckDurationV2FetchRequestPolicyReachableCommit() throws Exception {
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

		assertTrue(stats.getReachabilityCheckDuration() >= 0);
	}

