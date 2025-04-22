	@Test
	public void testRebaseShouldStopForEditInCaseOfConflict()
			throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Change file1").call();

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Change file1").call();

		RebaseResult result = git.rebase().setUpstream("HEAD~2")
				.runInteractively(new InteractiveHandler() {

					public void prepareSteps(List<RebaseTodoLine> steps) {
						steps.remove(0);
						steps.get(0).setAction(Action.EDIT);
					}

					public String modifyCommitMessage(String commit) {
						return commit;
					}
				}).call();
		assertEquals(Status.STOPPED
		git.add().addFilepattern(FILE1).call();
		result = git.rebase().setOperation(Operation.CONTINUE).call();
		assertEquals(Status.EDIT
	}

