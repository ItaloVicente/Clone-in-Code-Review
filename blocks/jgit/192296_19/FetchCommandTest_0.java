	@Test
	public void testFetchSimpleNegativeRefSpec() throws Exception {
		remoteGit.commit().setMessage("commit").call();

		FetchResult res = git.fetch().setRemote("test")
				.setRefSpecs("refs/heads/master:refs/heads/test"
						"^:refs/heads/test")
				.call();
		assertNull(res.getTrackingRefUpdate("refs/heads/test"));

		res = git.fetch().setRemote("test")
				.setRefSpecs("refs/heads/master:refs/heads/test"
						"^refs/heads/master")
				.call();
		assertNull(res.getTrackingRefUpdate("refs/heads/test"));
	}

	@Test
	public void negativeRefSpecFilterBySource() throws Exception {
		remoteGit.commit().setMessage("commit").call();
		Ref branchRef2 = remoteGit.branchCreate()
				.setName("refs/secret/topsecret/meta").call();
		remoteGit.commit().setMessage("commit1").call();

		FetchResult res = git.fetch().setRemote("test")
				.call();
		assertNotNull(res.getTrackingRefUpdate("refs/origins/heads/master"));
		assertNull(
				res.getTrackingRefUpdate("refs/origins/secret/topsecret/meta"));
	}

	@Test
	public void negativeRefSpecFilterByDestination() throws Exception {
		remoteGit.commit().setMessage("commit").call();
		Ref branchRef2 = remoteGit.branchCreate()
				.setName("refs/secret/topsecret/meta").call();
		remoteGit.commit().setMessage("commit1").call();

		FetchResult res = git.fetch().setRemote("test")
				.call();
		assertNotNull(res.getTrackingRefUpdate("refs/secret/heads/master"));
		assertNull(res.getTrackingRefUpdate("refs/secret/development/data"));
	}

