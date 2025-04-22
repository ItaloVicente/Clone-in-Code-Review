	@Test
	public void testMaliciousAbsolutePathIsOk()
			throws Exception {
		testMaliciousPath(true
	}

	@Test
	public void testMaliciousAbsolutePath() throws Exception {
		testMaliciousPath(false
	}

	@Test
	public void testMaliciousAbsoluteCurDrivePathWindows() throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setWindows();
		testMaliciousPath(false
	}

	@Test
	public void testMaliciousAbsoluteCurDrivePathWindowsOnUnix()
			throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setUnix();
		testMaliciousPath(true
	}

	@Test
	public void testMaliciousAbsoluteUNCPathWindows1() throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setWindows();
		testMaliciousPath(false
	}

	@Test
	public void testMaliciousAbsoluteUNCPathWindows1OnUnix() throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setUnix();
		testMaliciousPath(true
	}

	@Test
	public void testMaliciousAbsoluteUNCPathWindows2() throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setWindows();
		testMaliciousPath(false
	}

	@Test
	public void testMaliciousAbsoluteUNCPathWindows2OnUnix() throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setUnix();
		testMaliciousPath(false
	}

	@Test
	public void testMaliciousAbsoluteWindowsPath1() throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setWindows();
		testMaliciousPath(false
	}

	@Test
	public void testMaliciousAbsoluteWindowsPath1OnUnix() throws Exception {
		if (File.separatorChar == '\\')
		((MockSystemReader) SystemReader.getInstance()).setUnix();
		testMaliciousPath(true
	}

	@Test
	public void testMaliciousAbsoluteWindowsPath2() throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setCurrentPlatform();
		testMaliciousPath(false
	}

	@Test
	public void testMaliciousGitPath1()
			throws Exception {
		testMaliciousPath(false
	}

	@Test
	public void testMaliciousGitPath2() throws Exception {
		testMaliciousPath(false
	}

	@Test
	public void testMaliciousGitPath1Case() throws Exception {
		testMaliciousPath(false
	}

	@Test
	public void testMaliciousGitPath2Case() throws Exception {
		testMaliciousPath(false
	}

	@Test
	public void testMaliciousGitPathEndSpaceWindows() throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setWindows();
		testMaliciousPath(false
	}

	@Test
	public void testMaliciousGitPathEndSpaceUnixOk() throws Exception {
		if (File.separatorChar == '\\')
		((MockSystemReader) SystemReader.getInstance()).setUnix();
		testMaliciousPath(true
	}

	@Test
	public void testMaliciousGitPathEndDotWindows1() throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setWindows();
		testMaliciousPath(false
	}

	@Test
	public void testMaliciousGitPathEndDotWindows2() throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setWindows();
		testMaliciousPath(false
	}

	@Test
	public void testMaliciousGitPathEndDotWindows3() throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setWindows();
		testMaliciousPath(true
	}

	@Test
	public void testMaliciousGitPathEndDotUnixOk() throws Exception {
		if (File.separatorChar == '\\')
		((MockSystemReader) SystemReader.getInstance()).setUnix();
		testMaliciousPath(true
	}

	@Test
	public void testMaliciousPathDotDot() throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setCurrentPlatform();
		testMaliciousPath(false
	}

	@Test
	public void testMaliciousPathDot() throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setCurrentPlatform();
		testMaliciousPath(false
	}

	@Test
	public void testMaliciousPathEmpty() throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setCurrentPlatform();
		testMaliciousPath(false
	}

	@Test
	public void testMaliciousWindowsADS() throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setWindows();
		testMaliciousPath(false
	}

	@Test
	public void testMaliciousWindowsADSOnUnix() throws Exception {
		if (File.separatorChar == '\\')
		((MockSystemReader) SystemReader.getInstance()).setUnix();
		testMaliciousPath(true
	}

	@Test
	public void testForbiddenNamesOnWindowsEgCon() throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setWindows();
		testMaliciousPath(false
	}

	@Test
	public void testForbiddenNamesOnWindowsEgConDotSuffix() throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setWindows();
		testMaliciousPath(false
	}

	@Test
	public void testForbiddenNamesOnWindowsEgLpt1() throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setWindows();
		testMaliciousPath(false
	}

	@Test
	public void testForbiddenNamesOnWindowsEgLpt1DotSuffix() throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setWindows();
		testMaliciousPath(false
	}

	@Test
	public void testForbiddenNamesOnWindowsEgDotCon() throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setWindows();
		testMaliciousPath(true
	}

	@Test
	public void testForbiddenNamesOnWindowsEgLpr() throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setWindows();
		testMaliciousPath(true
	}

	@Test
	public void testForbiddenNamesOnWindowsEgCon1() throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setWindows();
		testMaliciousPath(true
	}

	@Test
	public void testForbiddenWindowsNamesOnUnixEgCon() throws Exception {
		if (File.separatorChar == '\\')
		testMaliciousPath(true
	}

	@Test
	public void testForbiddenWindowsNamesOnUnixEgLpt1() throws Exception {
		if (File.separatorChar == '\\')
		testMaliciousPath(true
	}

	private void testMaliciousPath(boolean good
			throws IOException
			RefNotFoundException
			MissingObjectException
		Git git = new Git(db);
		ObjectInserter newObjectInserter;
		newObjectInserter = git.getRepository().newObjectInserter();
		ObjectId blobId = newObjectInserter.insert(Constants.OBJ_BLOB
				"data".getBytes());
		newObjectInserter = git.getRepository().newObjectInserter();
		FileMode mode = FileMode.REGULAR_FILE;
		ObjectId insertId = blobId;
		for (int i = path.length - 1; i >= 0; --i) {
			TreeFormatter treeFormatter = new TreeFormatter();
			treeFormatter.append(path[i]
			insertId = newObjectInserter.insert(treeFormatter);
		}
		newObjectInserter = git.getRepository().newObjectInserter();
		CommitBuilder commitBuilder = new CommitBuilder();
		commitBuilder.setAuthor(author);
		commitBuilder.setCommitter(committer);
		commitBuilder.setMessage("foo");
		commitBuilder.setTreeId(insertId);
		ObjectId commitId = newObjectInserter.insert(commitBuilder);
		RevWalk revWalk = new RevWalk(git.getRepository());
		try {
			git.checkout().setStartPoint(revWalk.parseCommit(commitId))
					.setName("refs/heads/master").setCreateBranch(true).call();
			if (!good)
				fail("Checkout of Tree " + Arrays.asList(path) + " should fail");
		} catch (InvalidPathException e) {
			if (good)
				throw e;
			assertThat(e.getMessage()
		}
	}

