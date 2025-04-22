	@Test
	public void testRebaseInteractiveEdit() throws Exception {
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
						steps.get(0).action = Action.EDIT;
					}

					public String modifyCommitMessage(String commit) {
					}
				}).call();
		assertEquals(Status.STOPPED
		RevCommit toBeEditted = git.log().call().iterator().next();
		assertEquals("updated file1 on master"

		writeTrashFile("file1"
		git.commit().setAll(true).setAmend(true)
				.setMessage("edited commit message").call();
		res = git.rebase().setOperation(Operation.CONTINUE).call();

		checkFile(new File(db.getWorkTree()
		assertEquals(Status.OK
		Iterator<RevCommit> logIterator = git.log().all().call().iterator();
		String actualCommitMag = logIterator.next().getShortMessage();
		assertEquals("edited commit message"
	}

