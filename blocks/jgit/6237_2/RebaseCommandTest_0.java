
	@Test
	public void testRebaseShouldBeAbleToHandleEmptyLinesInRebaseTodoFile()
			throws IOException {
		String emptyLine = "\n";
		String todo = "pick 1111111 Commit 1\n" + emptyLine
				+ "pick 2222222 Commit 2\n" + emptyLine
				+ "# Comment line at end\n";
		write(getTodoFile()

		RebaseCommand rebaseCommand = git.rebase();
		List<Step> steps = rebaseCommand.loadSteps();
		assertEquals(2
		assertEquals("1111111"
		assertEquals("2222222"
	}

	private File getTodoFile() {
		File todoFile = new File(db.getDirectory()
				"rebase-merge/git-rebase-todo");
		return todoFile;
	}
