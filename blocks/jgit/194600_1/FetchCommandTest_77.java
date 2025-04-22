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
		remoteGit.branchCreate().setName("test").call();
		remoteGit.commit().setMessage("commit1").call();
		remoteGit.branchCreate().setName("dev").call();

		FetchResult res = git.fetch().setRemote("test")
				.call();
		assertNotNull(res.getTrackingRefUpdate("refs/origins/heads/master"));
		assertNull(res.getTrackingRefUpdate("refs/origins/heads/test"));
		assertNotNull(res.getTrackingRefUpdate("refs/origins/heads/dev"));
	}

	@Test
	public void negativeRefSpecFilterByDestination() throws Exception {
		remoteGit.commit().setMessage("commit").call();
		remoteGit.branchCreate().setName("meta").call();
		remoteGit.commit().setMessage("commit1").call();
		remoteGit.branchCreate().setName("data").call();

		FetchResult res = git.fetch().setRemote("test")
				.call();
		assertNotNull(res.getTrackingRefUpdate("refs/secret/heads/master"));
		assertNull(res.getTrackingRefUpdate("refs/secret/heads/meta"));
		assertNotNull(res.getTrackingRefUpdate("refs/secret/heads/data"));
	}

