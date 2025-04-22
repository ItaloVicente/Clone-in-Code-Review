
	@Test
	public void testCloneMirror() throws Exception {
		ObjectId head = createInitialCommit();
		RefUpdate ru = db.updateRef("refs/meta/foo/bar");
		ru.setNewObjectId(head);
		ru.update();

		File gitDir = db.getDirectory();
		String sourcePath = gitDir.getAbsolutePath();
		String targetPath = (new File(sourcePath)).getParentFile()
				.getParentFile().getAbsolutePath() + File.separator
				+ "target.git";
		String cmd = "git clone --mirror " + shellQuote(sourcePath) + " "
				+ shellQuote(targetPath);
		String[] result = execute(cmd);
		assertArrayEquals(
				new String[] { "Cloning into '" + targetPath + "'..."
				result);
		Git git2 = Git.open(new File(targetPath));
		List<Ref> branches = git2.branchList().call();
		assertEquals("expected 1 branch"
		assertTrue("expected bare repository"
		StoredConfig config = git2.getRepository().getConfig();
		RemoteConfig rc = new RemoteConfig(config
		assertTrue("expected mirror configuration"
		RefSpec fetchRefSpec = rc.getFetchRefSpecs().get(0);
		assertTrue("exected force udpate"
		assertNotNull(git2.getRepository().exactRef("refs/meta/foo/bar"));
	}
