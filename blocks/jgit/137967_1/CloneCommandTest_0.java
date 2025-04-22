	@Test
	public void testCloneWithPullMerge() throws Exception {
		File directory = createTempDirectory("testCloneRepository1");
		try (Git g = Git.init().setDirectory(directory).setBare(false).call()) {
			g.remoteAdd().setName(Constants.DEFAULT_REMOTE_NAME)
					.setUri(new URIish(fileUri())).call();
			PullResult result = g.pull().setRebase(false).call();
			assertTrue(result.isSuccessful());
			assertEquals("refs/heads/master"
					g.getRepository().getFullBranch());
			checkFile(new File(directory
		}
	}

	@Test
	public void testCloneWithPullRebase() throws Exception {
		File directory = createTempDirectory("testCloneRepository1");
		try (Git g = Git.init().setDirectory(directory).setBare(false).call()) {
			g.remoteAdd().setName(Constants.DEFAULT_REMOTE_NAME)
					.setUri(new URIish(fileUri())).call();
			PullResult result = g.pull().setRebase(true).call();
			assertTrue(result.isSuccessful());
			assertEquals("refs/heads/master"
					g.getRepository().getFullBranch());
			checkFile(new File(directory
		}
	}

