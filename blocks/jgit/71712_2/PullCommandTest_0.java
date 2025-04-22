	@Test
	public void testPullWithUntrackedStash() throws Exception {
		target.pull().call();

		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt").call();
		source.commit().setMessage("Source change in remote").call();

		writeToFile(new File(dbTarget.getWorkTree()
				"untracked");
		RevCommit stash = target.stashCreate().setIndexMessage("message here")
				.setIncludeUntracked(true).call();
		assertNotNull(stash);
		assertTrue(target.status().call().isClean());

		assertTrue(target.pull().call().isSuccessful());
		assertEquals("[SomeFile.txt
				indexState(dbTarget
		assertFalse(JGitTestUtil.check(dbTarget
		assertEquals("Source change"
				JGitTestUtil.read(dbTarget

		target.stashApply().setStashRef(stash.getName()).call();
		assertEquals("[SomeFile.txt
				indexState(dbTarget
		assertEquals("untracked"
		assertEquals("Source change"
				JGitTestUtil.read(dbTarget
	}

