	@Test
	public void stashedContentMergeXtheirs() throws Exception {
		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("more content").call();

		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("even content").call();

		writeTrashFile(PATH

		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content\nhead change\nmore content\n"
				read(committedFile));
		assertTrue(git.status().call().isClean());
		recorder.assertEvent(new String[] { PATH }

		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("committed change").call();
		recorder.assertNoEvent();

		git.stashApply().setContentMergeStrategy(ContentMergeStrategy.THEIRS)
				.call();
		recorder.assertEvent(new String[] { PATH }
		Status status = new StatusCommand(db).call();
		assertEquals('[' + PATH + ']'
		assertEquals(
				"content\nstashed change\nmore content\ncommitted change\n"
				read(PATH));
	}

	@Test
	public void stashedContentMergeXours() throws Exception {
		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("more content").call();

		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("even content").call();

		writeTrashFile(PATH

		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content\nhead change\nmore content\n"
				read(committedFile));
		assertTrue(git.status().call().isClean());
		recorder.assertEvent(new String[] { PATH }

		writeTrashFile(PATH
				"content\nnew head\nmore content\ncommitted change\n");
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("committed change").call();
		recorder.assertNoEvent();

		git.stashApply().setContentMergeStrategy(ContentMergeStrategy.OURS)
				.call();
		recorder.assertEvent(new String[] { PATH }
		assertTrue(git.status().call().isClean());
		assertEquals("content\nnew head\nmore content\ncommitted change\n"
				read(PATH));
	}

	@Test
	public void stashedContentMergeTheirs() throws Exception {
		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("more content").call();

		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("even content").call();

		writeTrashFile(PATH

		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content\nhead change\nmore content\n"
				read(committedFile));
		assertTrue(git.status().call().isClean());
		recorder.assertEvent(new String[] { PATH }

		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("committed change").call();
		recorder.assertNoEvent();

		git.stashApply().setStrategy(MergeStrategy.THEIRS).call();
		recorder.assertEvent(new String[] { PATH }
		Status status = new StatusCommand(db).call();
		assertEquals('[' + PATH + ']'
		assertEquals("content\nstashed change\nmore content\n"
	}

	@Test
	public void stashedContentMergeOurs() throws Exception {
		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("more content").call();

		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("even content").call();

		writeTrashFile(PATH

		RevCommit stashed = git.stashCreate().call();
		assertNotNull(stashed);
		assertEquals("content\nhead change\nmore content\n"
				read(committedFile));
		assertTrue(git.status().call().isClean());
		recorder.assertEvent(new String[] { PATH }

		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("committed change").call();
		recorder.assertNoEvent();

		git.stashApply().setStrategy(MergeStrategy.OURS).call();
		recorder.assertNoEvent();
		assertTrue(git.status().call().isClean());
		assertEquals("content\nmore content\ncommitted change\n"
	}

