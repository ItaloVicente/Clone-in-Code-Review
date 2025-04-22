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

	@Test
	public void testRebaseShouldStopForRewordInCaseOfConflict()
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
						steps.get(0).setAction(Action.REWORD);
					}

					public String modifyCommitMessage(String commit) {
						return "rewritten commit message";
					}
				}).call();
		assertEquals(Status.STOPPED
		git.add().addFilepattern(FILE1).call();
		result = git.rebase().runInteractively(new InteractiveHandler() {

			public void prepareSteps(List<RebaseTodoLine> steps) {
				steps.remove(0);
				steps.get(0).setAction(Action.REWORD);
			}

			public String modifyCommitMessage(String commit) {
				return "rewritten commit message";
			}
		}).setOperation(Operation.CONTINUE).call();
		assertEquals(Status.OK
		Iterator<RevCommit> logIterator = git.log().all().call().iterator();
		String actualCommitMag = logIterator.next().getShortMessage();
		assertEquals("rewritten commit message"

	}

	@Test
	public void testRebaseShouldSquashInCaseOfConflict() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Change file2").call();

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Change file1").call();

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Change file1").call();

		RebaseResult result = git.rebase().setUpstream("HEAD~3")
				.runInteractively(new InteractiveHandler() {

					public void prepareSteps(List<RebaseTodoLine> steps) {
						steps.get(0).setAction(Action.PICK);
						steps.remove(1);
						steps.get(1).setAction(Action.SQUASH);
					}

					public String modifyCommitMessage(String commit) {
						return "squashed message";
					}
				}).call();
		assertEquals(Status.STOPPED
		git.add().addFilepattern(FILE1).call();
		result = git.rebase().runInteractively(new InteractiveHandler() {

			public void prepareSteps(List<RebaseTodoLine> steps) {
				steps.get(0).setAction(Action.PICK);
				steps.remove(1);
				steps.get(1).setAction(Action.SQUASH);
			}

			public String modifyCommitMessage(String commit) {
				return "squashed message";
			}
		}).setOperation(Operation.CONTINUE).call();
		assertEquals(Status.OK
		Iterator<RevCommit> logIterator = git.log().all().call().iterator();
		String actualCommitMag = logIterator.next().getShortMessage();
		assertEquals("squashed message"
	}

	@Test
	public void testRebaseShouldFixupInCaseOfConflict() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Change file2").call();

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Change file1").call();

		writeTrashFile(FILE1
		writeTrashFile("file3"
		git.add().addFilepattern(FILE1).call();
		git.add().addFilepattern("file3").call();
		git.commit().setMessage("Change file1

		RebaseResult result = git.rebase().setUpstream("HEAD~3")
				.runInteractively(new InteractiveHandler() {

					public void prepareSteps(List<RebaseTodoLine> steps) {
						steps.get(0).setAction(Action.PICK);
						steps.remove(1);
						steps.get(1).setAction(Action.FIXUP);
					}

					public String modifyCommitMessage(String commit) {
						return commit;
					}
				}).call();
		assertEquals(Status.STOPPED
		git.add().addFilepattern(FILE1).call();
		result = git.rebase().runInteractively(new InteractiveHandler() {

			public void prepareSteps(List<RebaseTodoLine> steps) {
				steps.get(0).setAction(Action.PICK);
				steps.remove(1);
				steps.get(1).setAction(Action.FIXUP);
			}

			public String modifyCommitMessage(String commit) {
				return "commit";
			}
		}).setOperation(Operation.CONTINUE).call();
		assertEquals(Status.OK
		Iterator<RevCommit> logIterator = git.log().all().call().iterator();
		String actualCommitMsg = logIterator.next().getShortMessage();
		assertEquals("Change file2"
		actualCommitMsg = logIterator.next().getShortMessage();
		assertEquals("Add file1"
		assertTrue(new File(db.getWorkTree()

	}

