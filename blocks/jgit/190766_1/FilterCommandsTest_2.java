	@Test
	public void testBranchSwitch() throws Exception {
		FilterCommandRegistry.register(builtinCommandPrefix + "smudge"
				new TestCommandFactory('s'));
		FilterCommandRegistry.register(builtinCommandPrefix + "clean"
				new TestCommandFactory('c'));
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				builtinCommandPrefix + "smudge");
		config.setString("filter"
				builtinCommandPrefix + "clean");
		config.save();
		File aFile = writeTrashFile("a.txt"
		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("On test").call();
		git.checkout().setName("master").call();
		git.branchCreate().setName("other").call();
		git.checkout().setName("other").call();
		writeTrashFile("b.txt"
		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("On other").call();
		git.checkout().setName("test").call();
		checkFile(aFile
	}

	@Test
	public void testCheckoutSingleFile() throws Exception {
		FilterCommandRegistry.register(builtinCommandPrefix + "smudge"
				new TestCommandFactory('s'));
		FilterCommandRegistry.register(builtinCommandPrefix + "clean"
				new TestCommandFactory('c'));
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				builtinCommandPrefix + "smudge");
		config.setString("filter"
				builtinCommandPrefix + "clean");
		config.save();
		File aFile = writeTrashFile("a.txt"
		File attributes = writeTrashFile(".gitattributes"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("On test").call();
		git.checkout().setName("master").call();
		git.branchCreate().setName("other").call();
		git.checkout().setName("other").call();
		writeTrashFile("b.txt"
		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("On other").call();
		git.checkout().setName("master").call();
		assertFalse(aFile.exists());
		assertFalse(attributes.exists());
		git.checkout().setStartPoint("test").addPath("a.txt").call();
		checkFile(aFile
	}

	@Test
	public void testCheckoutSingleFile2() throws Exception {
		FilterCommandRegistry.register(builtinCommandPrefix + "smudge"
				new TestCommandFactory('s'));
		FilterCommandRegistry.register(builtinCommandPrefix + "clean"
				new TestCommandFactory('c'));
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				builtinCommandPrefix + "smudge");
		config.setString("filter"
				builtinCommandPrefix + "clean");
		config.save();
		File aFile = writeTrashFile("a.txt"
		File attributes = writeTrashFile(".gitattributes"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("On test").call();
		git.checkout().setName("master").call();
		git.branchCreate().setName("other").call();
		git.checkout().setName("other").call();
		writeTrashFile("b.txt"
		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("On other").call();
		git.checkout().setName("master").call();
		assertFalse(aFile.exists());
		assertFalse(attributes.exists());
		writeTrashFile(".gitattributes"
		git.checkout().setStartPoint("test").addPath("a.txt").call();
		checkFile(aFile
	}

	@Test
	public void testMerge() throws Exception {
		FilterCommandRegistry.register(builtinCommandPrefix + "smudge"
				new TestCommandFactory('s'));
		FilterCommandRegistry.register(builtinCommandPrefix + "clean"
				new TestCommandFactory('c'));
		StoredConfig config = git.getRepository().getConfig();
		config.setString("filter"
				builtinCommandPrefix + "smudge");
		config.setString("filter"
				builtinCommandPrefix + "clean");
		config.save();
		File aFile = writeTrashFile("a.txt"
		writeTrashFile(".gitattributes"
		git.add().addFilepattern(".").call();
		RevCommit aCommit = git.commit().setMessage("On test").call();
		git.checkout().setName("master").call();
		assertFalse(aFile.exists());
		git.branchCreate().setName("other").call();
		git.checkout().setName("other").call();
		writeTrashFile("b/b.txt"
		writeTrashFile("b/.gitattributes"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("On other").call();
		MergeResult result = git.merge().include(aCommit).call();
		assertEquals(MergeResult.MergeStatus.MERGED
		checkFile(aFile
	}

