	@Test
	public void testPerformanceLogV2FetchRequest() throws Exception {
		String commonInBlob = "abcdefghijklmnopqrstuvwxyz";

		RevBlob parentBlob = remote.blob(commonInBlob + "a");
		RevCommit parent = remote
				.commit(remote.tree(remote.file("foo"
		RevBlob childBlob = remote.blob(commonInBlob + "b");
		RevCommit child = remote
				.commit(remote.tree(remote.file("foo"

		remote.update("branch1"
		uploadPackV2((UploadPack up) -> {
			up.setPerformanceLogHook(eventRecords -> {
				assertNotNull(eventRecords);
				assertTrue(eventRecords.get(0).getName()
						.equals("reachability-check"));
				assertTrue(eventRecords.get(1).getName().equals("negotiation"));
			});
			up.setRequestPolicy(RequestPolicy.REACHABLE_COMMIT);
		}
				"want " + parent.toObjectId().getName() + "\n"
				"done\n"
	}

