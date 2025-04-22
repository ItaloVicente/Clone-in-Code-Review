
	@Test
	public void testMultipleEntriesIgnored() throws Exception {
		createFiles("dir/a");
		writeTrashFile(".gitignore"
		assertSameAsCGit();
	}

	@Test
	public void testMultipleEntriesNotIgnored() throws Exception {
		createFiles("dir/a");
		writeTrashFile(".gitignore"
		assertSameAsCGit("dir/a");
	}

	@Test
	public void testInfoExcludes() throws Exception {
		createFiles("dir/a"
		File gitDir = db.getDirectory();
		File info = new File(gitDir
		assertTrue(info.mkdirs());
		File infoExclude = new File(info
		Files.writeString(infoExclude.toPath()
		assertSameAsCGit("dir/b");
	}

	@Test
	public void testInfoExcludesPrecedence() throws Exception {
		createFiles("dir/a"
		writeTrashFile(".gitignore"
		File gitDir = db.getDirectory();
		File info = new File(gitDir
		assertTrue(info.mkdirs());
		File infoExclude = new File(info
		Files.writeString(infoExclude.toPath()
		assertSameAsCGit("dir/a"
	}

	@Test
	public void testCoreExcludes() throws Exception {
		createFiles("dir/a"
		writeTrashFile(".fake_git_ignore"
		assertSameAsCGit("dir/b");
	}

	@Test
	public void testInfoCoreExcludes() throws Exception {
		createFiles("dir/a"
		File gitDir = db.getDirectory();
		File info = new File(gitDir
		assertTrue(info.mkdirs());
		File infoExclude = new File(info
		Files.writeString(infoExclude.toPath()
		writeTrashFile(".fake_git_ignore"
		assertSameAsCGit("dir/b");
	}

	@Test
	public void testInfoCoreExcludesPrecedence() throws Exception {
		createFiles("dir/a"
		File gitDir = db.getDirectory();
		File info = new File(gitDir
		assertTrue(info.mkdirs());
		File infoExclude = new File(info
		Files.writeString(infoExclude.toPath()
		writeTrashFile(".fake_git_ignore"
		assertSameAsCGit("dir/a"
	}

	@Test
	public void testInfoCoreExcludesPrecedence2() throws Exception {
		createFiles("dir/a"
		File gitDir = db.getDirectory();
		File info = new File(gitDir
		assertTrue(info.mkdirs());
		File infoExclude = new File(info
		Files.writeString(infoExclude.toPath()
		writeTrashFile(".fake_git_ignore"
		assertSameAsCGit("dir/b");
	}
