	@Test
	public void testNothingToDoResult() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Add file2").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("updated file1 on master").call();

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("update file2 on side").call();

		RebaseResult res = git.rebase().setUpstream("HEAD~2")
				.runInteractively(new InteractiveHandler() {
					public void prepareSteps(List<Step> steps) {
						steps.clear();
					}

					public String modifyCommitMessage(String commit) {
					}
				}).call();
		assertEquals(Status.NOTHING_TO_DO
		assertEquals(RepositoryState.SAFE

		assertFalse(new File(db.getDirectory()
	}

