	@Test
	public void stashedApplyOnOtherBranch() throws Exception {
		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("more content").call();
		String path2 = "file2.txt";
		File file2 = writeTrashFile(path2
		git.add().addFilepattern(PATH).call();
		git.add().addFilepattern(path2).call();
		git.commit().setMessage("even content").call();

		String otherBranch = "otherBranch";
		git.branchCreate().setName(otherBranch).call();

		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("even content").call();

		git.checkout().setName(otherBranch).call();

		writeTrashFile(PATH
		git.add().addFilepattern(PATH).call();
		git.commit().setMessage("even more content").call();

		writeTrashFile(path2

		RevCommit stashed = git.stashCreate().call();

		assertNotNull(stashed);
		assertEquals("content\nmore content\n"
		assertEquals("otherBranch content"
				read(committedFile));
		assertTrue(git.status().call().isClean());

		git.checkout().setName("master").call();
		git.stashApply().call();
		assertEquals("content\nstashed change\nmore content\n"
		assertEquals("master content"
				read(committedFile));
	}

