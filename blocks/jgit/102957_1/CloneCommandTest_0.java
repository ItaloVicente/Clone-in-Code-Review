
	private void setUp2() throws IOException
			GitAPIException {
		final String fileName2 = "HelloWorld.txt";
		final String fileName3 = "file.txt";
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
		final List<RevCommit> result = new ArrayList<RevCommit>();
		final Iterator<RevCommit> it = iterable.iterator();
		while (it.hasNext()) {
			final RevCommit commit = it.next();
			result.add(commit);
		}
		return result;
	}

	@Test
	public void testCloneRepositoryWithDepthInfinite()
			throws IOException
			JGitInternalException
		this.setUp2();
		System.out.println("testCloneRepositoryWithDepthInfinite - begin");
		final String fileName1 = "Test.txt";
		final String fileName2 = "HelloWorld.txt";
		final String fileName3 = "file.txt";

		File directory = createTempDirectory(
				"testCloneRepositoryWithDepthInfinite");
		CloneCommand command = Git.cloneRepository();
		command.setDepth(Transport.DEPTH_INFINITE);
		command.setBranch("refs/heads/master");
		command.setBranchesToClone(
				Collections.singletonList("refs/heads/master"));
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);
		assertEquals(git2.getRepository().getFullBranch()
		assertEquals("refs/remotes/origin/master"
				git2.branchList().setListMode(ListMode.REMOTE).call()));
		System.out.println("testCloneRepositoryWithDepth0 - end");
		final Ref masterRef = git2.getRepository().findRef("refs/heads/master");
		assertNotNull(masterRef);
		assertEquals(2
		assertNotNull(db.resolve("tag-initial"));
		assertNotNull(db.resolve("tag-for-blob"));

		List<RevCommit> commits = getAsList(git.log().call());
		assertEquals(3
		RevCommit commit0 = commits.get(0);
		assertNotNull(commit0);
		System.out.println("	commit0='" + commit0.name() + "'");
		assertEquals("Final commit"
		RevCommit commit1 = commits.get(1);
		assertNotNull(commit1);
		System.out.println("	commit1='" + commit1.name() + "'");
		assertEquals("Third commit"
		RevCommit commit2 = commits.get(2);
		assertNotNull(commit2);
		System.out.println("	commit2='" + commit2.name() + "'");
		assertEquals("Initial commit"

		assertTrue(new File(git2.getRepository().getWorkTree()
				File.separatorChar + fileName1).exists());
		assertTrue(new File(git2.getRepository().getWorkTree()
				File.separatorChar + fileName2).exists());
		assertTrue(new File(git2.getRepository().getWorkTree()
				File.separatorChar + fileName3).exists());
	}

	@Test
	public void testCloneRepositoryWithDepth1() throws IOException
			JGitInternalException
		this.setUp2();
		System.out.println("testCloneRepositoryWithDepth1 - begin");
		final String fileName1 = "Test.txt";
		final String fileName2 = "HelloWorld.txt";
		final String fileName3 = "file.txt";

		File directory = createTempDirectory("testCloneRepositoryWithDepth1");
		CloneCommand command = Git.cloneRepository();
		command.setDepth(1);
		command.setBranch("refs/heads/master");
		command.setBranchesToClone(
				Collections.singletonList("refs/heads/master"));
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);
		assertEquals(git2.getRepository().getFullBranch()
		assertEquals("refs/remotes/origin/master"
				git2.branchList().setListMode(ListMode.REMOTE).call()));
		System.out.println("testCloneRepositoryWithDepth1 - end");
		assertEquals(0
		assertNull(git2.getRepository().resolve("tag-initial"));
		assertNull(git2.getRepository().resolve("tag-for-blob"));

		List<RevCommit> commits = getAsList(git.log().call());
		assertEquals(1
		RevCommit commit0 = commits.get(0);
		assertNotNull(commit0);
		assertEquals("Final commit"
		System.out.println("	commit0='" + commit0.name() + "'");

		assertTrue(new File(git2.getRepository().getWorkTree()
				File.separatorChar + fileName1).exists());
		assertTrue(new File(git2.getRepository().getWorkTree()
				File.separatorChar + fileName2).exists());
		assertTrue(new File(git2.getRepository().getWorkTree()
				File.separatorChar + fileName3).exists());

		final Ref masterRef = git2.getRepository().findRef("refs/heads/master");
		assertNotNull(masterRef);
		final Ref testRef = git2.getRepository()
				.findRef("refs/remotes/origin/test");
		assertNull(testRef);
	}

	@Test
	public void testCloneRepositoryWithDepth2() throws IOException
			JGitInternalException
		this.setUp2();
		System.out.println("testCloneRepositoryWithDepth2 - begin");
		final String fileName1 = "Test.txt";
		final String fileName2 = "HelloWorld.txt";
		final String fileName3 = "file.txt";

		File directory = createTempDirectory("testCloneRepositoryWithDepth2");
		CloneCommand command = Git.cloneRepository();
		command.setDepth(2);
		command.setBranch("refs/heads/master");
		command.setBranchesToClone(
				Collections.singletonList("refs/heads/master"));
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);
		assertEquals(git2.getRepository().getFullBranch()
		assertEquals("refs/remotes/origin/master"
				git2.branchList().setListMode(ListMode.REMOTE).call()));
		System.out.println("testCloneRepositoryWithDepth2 - end");
		assertEquals(0
		assertNull(git2.getRepository().resolve("tag-initial"));
		assertNull(git2.getRepository().resolve("tag-for-blob"));

		List<RevCommit> commits = getAsList(git.log().call());
		assertEquals(2
		RevCommit commit0 = commits.get(0);
		assertNotNull(commit0);
		assertEquals("Final commit"
		RevCommit commit1 = commits.get(1);
		assertNotNull(commit1);
		assertEquals("Third commit"

		assertTrue(new File(git2.getRepository().getWorkTree()
				File.separatorChar + fileName1).exists());
		assertTrue(new File(git2.getRepository().getWorkTree()
				File.separatorChar + fileName2).exists());
		assertTrue(new File(git2.getRepository().getWorkTree()
				File.separatorChar + fileName3).exists());

		final Ref masterRef = git2.getRepository().findRef("refs/heads/master");
		assertNotNull(masterRef);
		final Ref testRef = git2.getRepository()
				.findRef("refs/remotes/origin/test");
		assertNull(testRef);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCloneRepositoryWithNegativeDepth()
			throws JGitInternalException
		CloneCommand command = Git.cloneRepository();
		command.setDepth(-3);
	}

