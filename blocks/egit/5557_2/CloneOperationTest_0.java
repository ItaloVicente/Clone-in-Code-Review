	@Test
	public void testCloneNoCheckout() throws Exception {
		URIish uri = new URIish("file:///"
				+ repository1.getRepository().getDirectory().toString());
		CloneOperation clop = new CloneOperation(uri, true, null, workdir2,
				null, "origin", 0);
		clop.run(null);

		Repository clonedRepo = new FileRepository(new File(workdir2,
				Constants.DOT_GIT));
		assertEquals(
				"",
				uri.toString(),
				clonedRepo.getConfig().getString(
						ConfigConstants.CONFIG_REMOTE_SECTION, "origin", "url"));
		assertEquals(
				"",
				"+refs/heads/*:refs/remotes/origin/*",
				clonedRepo.getConfig().getString(
						ConfigConstants.CONFIG_REMOTE_SECTION, "origin",
						"fetch"));

		File file = new File(workdir2, "file1.txt");
		assertFalse(file.exists());
		String[] filesInWorkDir = workdir2.list();
		assertEquals(1, filesInWorkDir.length);
		assertEquals(".git", filesInWorkDir[0]);
		assertFalse(new File(workdir2, ".git/index").exists());
	}

