	@Test
	public void testInteractiveRebaseWithModificationShouldNotDeleteDataOnAbort()
			throws Exception {
		writeTrashFile("file0"
		writeTrashFile(FILE1
		git.add().addFilepattern("file0").addFilepattern(FILE1).call();
		git.commit().setMessage("commit1").call();

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit2").call();

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		writeTrashFile("file0"
		git.add().addFilepattern("file0").addFilepattern(FILE1).call();
		writeTrashFile("file0"

		RebaseResult result = git.rebase().setUpstream("HEAD~2")
				.runInteractively(new InteractiveHandler() {

					public void prepareSteps(List<RebaseTodoLine> steps) {
						steps.get(0).setAction(Action.EDIT);
						steps.get(1).setAction(Action.PICK);
					}

					public String modifyCommitMessage(String commit) {
						return commit;
					}
				}).call();
		if (result.getStatus() == Status.EDIT)
			git.rebase().setOperation(Operation.ABORT).call();

		checkFile(new File(db.getWorkTree()
		checkFile(new File(db.getWorkTree()
				"modified file1 a second time");
		assertEquals("[file0
				+ "[file1
				indexState(CONTENT));

	}

