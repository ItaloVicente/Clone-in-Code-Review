
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

	private List<RevCommit> getAsList(Iterable<RevCommit> iterable) {
		assertNotNull(iterable);
		final List<RevCommit> result = new ArrayList<>();
		final Iterator<RevCommit> it = iterable.iterator();
		while (it.hasNext()) {
			final RevCommit commit = it.next();
			result.add(commit);
		}
		return result;
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCloneRepositoryWithNegativeDepth()
			throws JGitInternalException
		CloneCommand command = Git.cloneRepository();
		command.setDepth(-3);
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
	public void testCloneRepositoryWithDepth2000() throws IOException
			JGitInternalException
		this.setUp2();
		final CloneCommand cmd = helperForDepthTestSetupCloneCommand(
				"testCloneRepositoryWithDepth2000"
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
				Transport.DEPTH_INFINITE);
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
		command.setDepth(depth);
		command.setDirectory(directory);
		command.setURI(fileUri());
		return command;
	}

	private Git helperForDepthTestSetupGit(final CloneCommand command)
			throws IOException
		final Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);
		assertEquals("refs/heads/master"
		return git2;
	}

	private void helperForDepthTestCheckRefs(final Git git2
			final boolean hasMaster
			throws JGitInternalException
		final StringBuilder expectedRefsList = new StringBuilder("");
		boolean first = true;
		if (hasMaster) {
			expectedRefsList.append("refs/remotes/origin/master");
		}
		if (hasTest) {
			if (first) {
				expectedRefsList.append("
			}
			expectedRefsList.append("refs/remotes/origin/test");
		}
		final String expectedRefs = expectedRefsList.toString();
		final String actualRefs = allRefNames(
				git2.branchList().setListMode(ListMode.REMOTE).call());
		assertEquals(expectedRefs
	}

	private void helperForDepthTestCheckTags(final Git git2
			final boolean initialTag
			final boolean anotherFileTag)
			throws JGitInternalException
		int tagsCount = 0;
		if (initialTag) {
			tagsCount++;
		}
		if (blobTag) {
			tagsCount++;
		}
		if (anotherFileTag) {
			tagsCount++;
		}

		assertEquals(tagsCount
		final ObjectId initial = git2.getRepository().resolve("tag-initial");
		if (initialTag) {
			assertNotNull(initial);
		} else {
			assertNull(initial);
		}
		final ObjectId blob = git2.getRepository().resolve("tag-for-blob");
		if (blobTag) {
			assertNotNull(blob);
		} else {
			assertNull(blob);
		}
		final ObjectId another = git2.getRepository()
				.resolve("another-file.txt");
		if (anotherFileTag) {
			assertNotNull(another);
		} else {
			assertNull(another);
		}
	}

	private void helperForDepthTestCheckBranches(final Git git2
			final boolean branchMaster
			throws GitAPIException {
		int branchCount = 0;
		if (branchMaster) {
			branchCount++;
		}
		if (branchTest) {
			branchCount++;
		}
		final List<Ref> branches = git2.branchList().call();
		assertNotNull(branches);
		int i = 0;
		for (Ref ref : branches) {
			System.out.println("ref('" + i + "')='" + ref + "'");
			i++;
		}
		assertEquals(branchCount
	}

	private void helperForDepthTestCheckCommits(final Git git2
			final boolean finalCommit
			final boolean initialCommit)
			throws JGitInternalException
		int commitCount = 0;
		if (finalCommit) {
			commitCount++;
		}
		if (thirdCommit) {
			commitCount++;
		}
		if (initialCommit) {
			commitCount++;
		}
		final List<RevCommit> commits = getAsList(git2.log().call());
		assertEquals(commitCount
		if (finalCommit) {
			final RevCommit commit0 = commits.get(0);
			assertNotNull(commit0);
			assertEquals("Final commit"
		}
		if (thirdCommit) {
			final RevCommit commit1 = commits.get(1);
			assertNotNull(commit1);
			assertEquals("Third commit"
		}
		if (initialCommit) {
			final RevCommit commit2 = commits.get(2);
			assertNotNull(commit2);
			System.out.println("	commit2='" + commit2.name() + "'");
			assertEquals("Initial commit"
		}
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

	private void helperForDepthTestCheckShallowFile(final Git git2
			final String... expectedIds) throws IOException {
		final FileBasedShallow shallow = new FileBasedShallow(
				git2.getRepository());
		final List<ObjectId> ids = shallow.read();
		assertEquals(expectedIds.length
		for (int i = 0; i < expectedIds.length; i++) {
			final String expectedId = expectedIds[i];
			final ObjectId id = ids.get(i);
			assertEquals(expectedId
		}
	}


