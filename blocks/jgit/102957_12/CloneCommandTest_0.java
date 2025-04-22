
	private void setUp2() throws IOException
			GitAPIException {
		final String fileName1 = "another-file.txt";
		final String fileName2 = "HelloWorld.txt";
		final String fileName3 = "file.txt";
		git.checkout().setName("test").call();
		writeTrashFile(fileName1
		git.add().addFilepattern(fileName1).call();
		git.commit().setMessage(fileName1).call();
		git.tag().setName(fileName1).call();

		git.checkout().setName("master")
				.call();
		assertEquals(git.getRepository().getFullBranch()

		writeTrashFile(fileName2
		git.add().addFilepattern(fileName2).call();
		git.commit().setMessage("Third commit").call();
		writeTrashFile(fileName3
		git.add().addFilepattern(fileName3).call();
		git.commit().setMessage("Final commit").call();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCloneRepositoryWithNegativeDepth()
			throws JGitInternalException
		CloneCommand command = Git.cloneRepository();
		command.setDepth(new Depth(-3));
	}

	@Test
	public void testCloneRepositoryWithDepth1() throws IOException
			JGitInternalException
		this.setUp2();
		final CloneCommand cmd = helperForDepthTestSetupCloneCommand(
				"testCloneRepositoryWithDepth1"
		final Git git2 = helperForDepthTestSetupGit(cmd);
		helperForDepthTestCheckShallowFile(git2
				"c21676d47a0f506cfa26596c5bb8f33430603054");
		helperForDepthTestCheckCommits(git2
		helperForDepthTestCheckFilesExist(git2);
		helperForDepthTestCheckTags(git2
		helperForDepthTestCheckBranches(git2
		helperForDepthTestCheckRefs(git2

	}

	@Test
	public void testCloneRepositoryWithDepth1WithTestBranch()
			throws IOException
		this.setUp2();
		final CloneCommand cmd = helperForDepthTestSetupCloneCommand(
				"testCloneRepositoryWithDepth1WithTestBranch"
		final List<String> branchesToClone = new ArrayList<>();
		branchesToClone.add("refs/heads/master");
		branchesToClone.add("refs/heads/test");
		cmd.setBranchesToClone(branchesToClone);
		final Git git2 = helperForDepthTestSetupGit(cmd);
		helperForDepthTestCheckShallowFile(git2
				"4c593aa21f6c0b95881a9f49cb03bd6dcd974535"
				"c21676d47a0f506cfa26596c5bb8f33430603054");
		helperForDepthTestCheckCommits(git2
		helperForDepthTestCheckFilesExist(git2);
		helperForDepthTestCheckTags(git2
		helperForDepthTestCheckBranches(git2
		helperForDepthTestCheckRefs(git2

	}

	@Test
	public void testCloneRepositoryWithDepth2() throws IOException
			JGitInternalException
		this.setUp2();
		final CloneCommand cmd = helperForDepthTestSetupCloneCommand(
				"testCloneRepositoryWithDepth2"
				final Git git2 = helperForDepthTestSetupGit(cmd);
		helperForDepthTestCheckShallowFile(git2
				"9007666656678b23b58b5d88ec76107a9948c006");
		helperForDepthTestCheckCommits(git2
		helperForDepthTestCheckFilesExist(git2);
		helperForDepthTestCheckTags(git2
		helperForDepthTestCheckBranches(git2
		helperForDepthTestCheckRefs(git2
	}

	@Test
	public void testCloneRepositoryWithDepth2WithTestBranch()
			throws IOException
		this.setUp2();
		final CloneCommand cmd = helperForDepthTestSetupCloneCommand(
				"testCloneRepositoryWithDepth2WithTestBranch"
		final List<String> branchesToClone = new ArrayList<>();
		branchesToClone.add("refs/heads/master");
		branchesToClone.add("refs/heads/test");
		cmd.setBranchesToClone(branchesToClone);
		final Git git2 = helperForDepthTestSetupGit(cmd);
		helperForDepthTestCheckShallowFile(git2
				"271b5e2a665d3209d1873e137e8766c878e8e9cd"
				"9007666656678b23b58b5d88ec76107a9948c006");
		helperForDepthTestCheckCommits(git2
		helperForDepthTestCheckFilesExist(git2);
		helperForDepthTestCheckTags(git2
		helperForDepthTestCheckBranches(git2
		helperForDepthTestCheckRefs(git2
	}

	@Test
	public void testCloneRepositoryWithDepth3()
			throws IOException
		this.setUp2();
		final CloneCommand cmd = helperForDepthTestSetupCloneCommand(
				"testCloneRepositoryWithDepth3"
		final Git git2 = helperForDepthTestSetupGit(cmd);
		helperForDepthTestCheckShallowFile(git2
				"d0b1ef2b3dea02bb2ca824445c04e6def012c32c");
		helperForDepthTestCheckCommits(git2
		helperForDepthTestCheckFilesExist(git2);
		helperForDepthTestCheckTags(git2
		helperForDepthTestCheckBranches(git2
		helperForDepthTestCheckRefs(git2
	}

	@Test
	public void testCloneRepositoryWithDepth3WithTestBranch()
			throws IOException
		this.setUp2();
		final CloneCommand cmd = helperForDepthTestSetupCloneCommand(
				"testCloneRepositoryWithDepth3WithTestBranch"
		final List<String> branchesToClone = new ArrayList<>();
		branchesToClone.add("refs/heads/master");
		branchesToClone.add("refs/heads/test");
		cmd.setBranchesToClone(branchesToClone);
		final Git git2 = helperForDepthTestSetupGit(cmd);
		helperForDepthTestCheckShallowFile(git2
				"d0b1ef2b3dea02bb2ca824445c04e6def012c32c");
		helperForDepthTestCheckCommits(git2
		helperForDepthTestCheckFilesExist(git2);
		helperForDepthTestCheckTags(git2
		helperForDepthTestCheckBranches(git2
		helperForDepthTestCheckRefs(git2
	}

	@Test
	public void testCloneRepositoryWithDepth4() throws IOException
			JGitInternalException
		this.setUp2();
		final CloneCommand cmd = helperForDepthTestSetupCloneCommand(
				"testCloneRepositoryWithDepth4"
		final Git git2 = helperForDepthTestSetupGit(cmd);
		helperForDepthTestCheckShallowFile(git2);
		helperForDepthTestCheckCommits(git2
		helperForDepthTestCheckFilesExist(git2);
		helperForDepthTestCheckTags(git2
		helperForDepthTestCheckBranches(git2
		helperForDepthTestCheckRefs(git2
	}

	@Test
	public void testCloneRepositoryWithDepth4WithTestBranch()
			throws IOException
		this.setUp2();
		final CloneCommand cmd = helperForDepthTestSetupCloneCommand(
				"testCloneRepositoryWithDepth4WithTestBranch"
		final List<String> branchesToClone = new ArrayList<>();
		branchesToClone.add("refs/heads/master");
		branchesToClone.add("refs/heads/test");
		cmd.setBranchesToClone(branchesToClone);
		final Git git2 = helperForDepthTestSetupGit(cmd);
		helperForDepthTestCheckShallowFile(git2);
		helperForDepthTestCheckCommits(git2
		helperForDepthTestCheckFilesExist(git2);
		helperForDepthTestCheckTags(git2
		helperForDepthTestCheckBranches(git2
		helperForDepthTestCheckRefs(git2
	}

	@Test
	public void testCloneRepositoryWithDepthInfinite()
			throws IOException
		this.setUp2();
		final CloneCommand cmd = helperForDepthTestSetupCloneCommand(
				"testCloneRepositoryWithDepthInfinite"
				Depth.DEPTH_INFINITE);
		final Git git2 = helperForDepthTestSetupGit(cmd);
		helperForDepthTestCheckShallowFile(git2);
		helperForDepthTestCheckCommits(git2
		helperForDepthTestCheckFilesExist(git2);
		helperForDepthTestCheckTags(git2
		helperForDepthTestCheckBranches(git2
		helperForDepthTestCheckRefs(git2

	}

	private CloneCommand helperForDepthTestSetupCloneCommand(
			final String testName
			final int depth)
			throws IOException
		final File directory = createTempDirectory(testName);
		final CloneCommand command = Git.cloneRepository();
		command.setDepth(new Depth(depth));
		command.setDirectory(directory);
		command.setURI(fileUri());
		return command;
	}

	protected FetchCommand helperForDepthTestSetupFetchCommand(Git git2
			final int depth
			final boolean testBranch)
			throws JGitInternalException
		final FetchCommand command = git2.fetch();
		command.setDepth(new Depth(depth));
		command.setTagOpt(TagOpt.AUTO_FOLLOW);
		final List<RefSpec> refSpecs = new ArrayList<>();
		if (masterBranch) {
			final RefSpec refSpec = getRefSpec(Constants.MASTER);
			refSpecs.add(refSpec);
		}
		if (testBranch) {
			final RefSpec refSpec = getRefSpec("test");
			refSpecs.add(refSpec);
		}
		command.setRefSpecs(refSpecs);
		command.call();
		return command;
	}

	private RefSpec getRefSpec(final String branchName) {
		final String srcBranchName = Constants.R_HEADS + branchName;
		final String dstBranchName = Constants.R_REMOTES
				+ Constants.DEFAULT_REMOTE_NAME + "/" + branchName;
		RefSpec refSpec = new RefSpec();
		refSpec = refSpec.setForceUpdate(true);
		refSpec = refSpec.setSourceDestination(srcBranchName
		return refSpec;
	}

	private Git helperForDepthTestSetupGit(final CloneCommand command)
			throws IOException
		final Git git2 = command.call();
		assertNotNull(git2);
		addRepoToClose(git2.getRepository());
		assertEquals("refs/heads/master"
		return git2;
	}

	private void helperForDepthTestCheckShallowFile(final Git git2
			final String... expectedIds) throws IOException {
		final FileBasedShallow shallow = new FileBasedShallow(
				git2.getRepository());
		final List<ObjectId> actualIds = shallow.read();
		final List<String> actual = getAsStringList(actualIds);
		final List<String> expected = Arrays.asList(expectedIds);
		assertEquals(expected
	}

	private void helperForDepthTestCheckCommits(final Git git2
			final boolean finalCommit
			final boolean initialCommit)
			throws JGitInternalException
		final Iterable<RevCommit> actualCommits = git2.log().call();
		final List<String> actual = getAsStringList(actualCommits);
		final List<String> expected = new ArrayList<>();
		if (finalCommit) {
			expected.add("Final commit");
		}
		if (thirdCommit) {
			expected.add("Third commit");
		}
		if (initialCommit) {
			expected.add("Initial commit");
		}
		assertEquals(expected
	}

	private void helperForDepthTestCheckFilesExist(final Git git2) {
		final String fileName1 = "Test.txt";
		final String fileName2 = "HelloWorld.txt";
		final String fileName3 = "file.txt";
		assertTrue(new File(git2.getRepository().getWorkTree()
				File.separatorChar + fileName1).exists());
		assertTrue(new File(git2.getRepository().getWorkTree()
				File.separatorChar + fileName2).exists());
		assertTrue(new File(git2.getRepository().getWorkTree()
				File.separatorChar + fileName3).exists());
	}

	private void helperForDepthTestCheckTags(final Git git2
			final boolean initialTag
			final boolean anotherFileTag)
			throws JGitInternalException {
		final Iterable<Ref> actualRefs = git2.getRepository().getTags()
				.values();
		final List<String> actual = getAsStringList(actualRefs);
		final List<String> expected = new ArrayList<>();
		if (anotherFileTag) {
			expected.add("refs/tags/another-file.txt");
		}
		if (blobTag) {
			expected.add("refs/tags/tag-for-blob");
		}
		if (initialTag) {
			expected.add("refs/tags/tag-initial");
		}
		assertEquals(expected
	}

	private void helperForDepthTestCheckBranches(final Git git2
			final boolean branchMaster
			throws GitAPIException {
		final ListBranchCommand cmd = git2.branchList();
		cmd.setListMode(ListMode.ALL);
		final List<Ref> actualBranches = cmd.call();
		final List<String> actual = getAsStringList(actualBranches);
		final List<String> expected = new ArrayList<>();
		if (branchMaster) {
			expected.add("refs/heads/master");
			expected.add("refs/remotes/origin/master");
		}
		if (branchTest) {
			expected.add("refs/remotes/origin/test");
		}
		assertEquals(expected
	}

	private void helperForDepthTestCheckRefs(final Git git2
			final boolean hasMaster
			throws JGitInternalException
		final List<Ref> actualRefs = git2.branchList()
				.setListMode(ListMode.REMOTE).call();
		final List<String> actual = getAsStringList(actualRefs);
		final List<String> expected = new ArrayList<>();
		if (hasMaster) {
			expected.add("refs/remotes/origin/master");
		}
		if (hasTest) {
			expected.add("refs/remotes/origin/test");
		}
		assertEquals(expected
	}

	private List<String> getAsStringList(final Iterable<?> iterable) {
		assertNotNull(iterable);
		final List<String> result = new ArrayList<>();
		final Iterator<?> it = iterable.iterator();
		while (it.hasNext()) {
			final Object obj = it.next();
			final String name = getStringFor(obj);
			result.add(name);
		}
		return result;
	}

	private String getStringFor(final Object object) {
		if (object instanceof RevCommit) {
			return ((RevCommit) object).getShortMessage();
		}
		else if (object instanceof ObjectId) {
			return ((ObjectId) object).getName();
		}
		else if (object instanceof Ref) {
			return ((Ref) object).getName();
		}
		return object.toString();
	}

