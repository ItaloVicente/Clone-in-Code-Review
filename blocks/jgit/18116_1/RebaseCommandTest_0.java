	@Test
	public void testRebaseEndsIfLastStepIsEdit() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Add file2\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		git.rebase().setUpstream("HEAD~1")
				.runInteractively(new InteractiveHandler() {

					public void prepareSteps(List<RebaseTodoLine> steps) {
						steps.get(0).setAction(Action.EDIT);
					}

					public String modifyCommitMessage(String commit) {
						return commit;
					}
				}).call();
		git.commit().setAmend(true)
				.setMessage("Add file2\nnew line\nanother line").call();
		RebaseResult result = git.rebase().setOperation(Operation.CONTINUE)
				.call();
		assertEquals(Status.OK

	}

