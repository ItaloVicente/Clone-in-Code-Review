
	@Test
	public void rewordWithChangeId() throws Exception {
		RewordCommitOperation op = new RewordCommitOperation(
				testRepository.getRepository(), commit,
				"new message\n\nChange-Id: I0000000000000000000000000000000000000000\n",
				true);
		op.execute(new NullProgressMonitor());

		LogCommand log;
		try (Git git = new Git(testRepository.getRepository())) {
			log = git.log();
		}
		RevCommit newCommit = log.call().iterator().next();
		String newMessage = newCommit.getFullMessage();

		assertTrue(newMessage.startsWith("new message\n\n"));
		checkChangeId(newMessage);
	}

	@Test
	public void rewordWithNewChangeId() throws Exception {
		RewordCommitOperation op = new RewordCommitOperation(
				testRepository.getRepository(), commit, "new message\n", true);
		op.execute(new NullProgressMonitor());

		LogCommand log;
		try (Git git = new Git(testRepository.getRepository())) {
			log = git.log();
		}
		RevCommit newCommit = log.call().iterator().next();
		String newMessage = newCommit.getFullMessage();

		assertTrue(newMessage.startsWith("new message\n\n"));
		checkChangeId(newMessage);
	}

	@Test
	public void rewordWithNewChangeIdNoNewline() throws Exception {
		RewordCommitOperation op = new RewordCommitOperation(
				testRepository.getRepository(), commit, "new message", true);
		op.execute(new NullProgressMonitor());

		LogCommand log;
		try (Git git = new Git(testRepository.getRepository())) {
			log = git.log();
		}
		RevCommit newCommit = log.call().iterator().next();
		String newMessage = newCommit.getFullMessage();

		assertTrue(newMessage.startsWith("new message\n\n"));
		checkChangeId(newMessage);
	}

	@Test
	public void rewordWithExistingChangeId() throws Exception {
		RewordCommitOperation op = new RewordCommitOperation(
				testRepository.getRepository(), commit,
				"new message\n\nChange-Id: I1230000000000000000000000000000000000321\n",
				true);
		op.execute(new NullProgressMonitor());

		LogCommand log;
		try (Git git = new Git(testRepository.getRepository())) {
			log = git.log();
		}
		RevCommit newCommit = log.call().iterator().next();
		String newMessage = newCommit.getFullMessage();

		assertEquals(
				"new message\n\nChange-Id: I1230000000000000000000000000000000000321\n",
				newMessage);
	}

	private void checkChangeId(String message) {
		assertFalse(message.contains(
				"\nChange-Id: I0000000000000000000000000000000000000000\n"));
		Matcher m = CHANGE_ID.matcher(message);
		int start = 0;
		boolean found = false;
		while (m.find(start)) {
			if (found) {
				assertEquals("Expected only one Change-Id", message, "");
			}
			found = true;
			start = m.end();
		}
		assertTrue(found);
	}
