		Status status = new StatusCommand(db).call();
		assertEquals(1
		assertEquals(
				"content\n<<<<<<< HEAD\n=======\nstashed change\n>>>>>>> stash\nmore content\ncommitted change\n"
				read(PATH));
	}

	@Test
	public void workingDirectoryContentMerge() throws Exception {
		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("more content").call();

		writeTrashFile(PATH

		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content\nmore content\n"
		assertTrue(git.status().call().isClean());

		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("committed change").call();

		git.stashApply().call();
		assertEquals(
				"content\nstashed change\nmore content\ncommitted change\n"
				read(committedFile));
