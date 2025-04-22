	@Test
	public void testCherryPickOverExecutableChangeOnNonExectuableFileSystem()
			throws Exception {
		Git git = new Git(db);
		File file = writeTrashFile("test.txt"
		assertNotNull(git.add().addFilepattern("test.txt").call());
		assertNotNull(git.commit().setMessage("commit1").call());

		assertNotNull(git.checkout().setCreateBranch(true).setName("a").call());

		writeTrashFile("test.txt"
		assertNotNull(git.add().addFilepattern("test.txt").call());
		RevCommit commit2 = git.commit().setMessage("commit2").call();
		assertNotNull(commit2);

		assertNotNull(git.checkout().setName(Constants.MASTER).call());

		DirCache cache = db.lockDirCache();
		cache.getEntry("test.txt").setFileMode(FileMode.EXECUTABLE_FILE);
		cache.write();
		assertTrue(cache.commit());
		cache.unlock();
		db.getFS().setExecute(file

		assertNotNull(git.commit().setMessage("commit3").call());
		git.getRepository()
				.getConfig()
				.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_FILEMODE

		CherryPickResult result = git.cherryPick().include(commit2).call();
		assertNotNull(result);
		assertEquals(CherryPickStatus.OK
	}

