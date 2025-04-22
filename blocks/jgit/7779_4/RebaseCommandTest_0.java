	@Test
	public void testRebaseIteractiveEditAction() throws Exception {
		String emptyLine = "\n";
		String todo = "pick 1111111 Commit 1\n" + emptyLine
				+ "reword 2222222 Commit 2\n" + emptyLine
				+ "# Comment line at end\n";
		write(getTodoFile()

		RebaseCommand rebaseCommand = git.rebase();
		List<Step> steps = rebaseCommand.loadSteps();

		assertEquals(2
		assertEquals("1111111"
		assertEquals("2222222"
		assertEquals(Action.REWORD
	}

	@Test
	public void testRebaseInteractiveReword() throws Exception {
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
						steps.get(0).action = Action.REWORD;
					}
					public String modifyCommitMessage(String commit) {
						return "rewritten commit message";
					}
				}).call();
		assertTrue(new File(db.getWorkTree()
		checkFile(new File(db.getWorkTree()
		assertEquals(Status.OK
		Iterator<RevCommit> logIterator = git.log().all().call().iterator();
		String actualCommitMag = logIterator.next().getShortMessage();
		assertEquals("rewritten commit message"
	}

