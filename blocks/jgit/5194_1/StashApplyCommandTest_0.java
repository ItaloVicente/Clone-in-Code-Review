
	@Test
	public void workingDirectoryContentConflict() throws Exception {
		writeTrashFile(PATH

		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"
		assertTrue(git.status().call().isClean());

		writeTrashFile(PATH

		try {
			git.stashApply().call();
			fail("Exception not thrown");
		} catch (JGitInternalException e) {
			assertTrue(e.getCause() instanceof CheckoutConflictException);
		}
	}

	@Test
	public void indexContentConflict() throws Exception {
		writeTrashFile(PATH

		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"
		assertTrue(git.status().call().isClean());

		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		writeTrashFile(PATH

		try {
			git.stashApply().call();
			fail("Exception not thrown");
		} catch (JGitInternalException e) {
			assertTrue(e.getCause() instanceof CheckoutConflictException);
		}
	}

	@Test
	public void workingDirectoryEditPreCommit() throws Exception {
		writeTrashFile(PATH

		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content"
		assertTrue(git.status().call().isClean());

		String path2 = "file2.txt";
		writeTrashFile(path2
		git.add().addFilepattern(path2).call();
		assertNotNull(git.commit().setMessage("adding file").call());

		ObjectId unstashed = git.stashApply().call();
		assertEquals(stashed

		Status status = git.status().call();
		assertTrue(status.getAdded().isEmpty());
		assertTrue(status.getChanged().isEmpty());
		assertTrue(status.getConflicting().isEmpty());
		assertTrue(status.getMissing().isEmpty());
		assertTrue(status.getRemoved().isEmpty());
		assertTrue(status.getUntracked().isEmpty());

		assertEquals(1
		assertTrue(status.getModified().contains(PATH));
	}

	@Test
	public void unstashNonStashCommit() throws Exception {
		try {
			git.stashApply().setStashRef(head.name()).call();
			fail("Exception not thrown");
		} catch (JGitInternalException e) {
			assertEquals(MessageFormat.format(
					JGitText.get().stashCommitMissingTwoParents
					e.getMessage());
		}
	}

	@Test
	public void unstashNoHead() throws Exception {
		Repository repo = createWorkRepository();
		try {
			Git.wrap(repo).stashApply().call();
			fail("Exception not thrown");
		} catch (NoHeadException e) {
			assertNotNull(e.getMessage());
		}
	}

	@Test
	public void noStashedCommits() throws Exception {
		try {
			git.stashApply().call();
			fail("Exception not thrown");
		} catch (InvalidRefNameException e) {
			assertNotNull(e.getMessage());
		}
	}
