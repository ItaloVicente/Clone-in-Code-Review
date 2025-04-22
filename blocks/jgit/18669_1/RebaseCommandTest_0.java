	@Test
	public void testRebaseShouldBeAbleToHandleLinesWithoutCommitMessageInRebaseTodoFile()
			throws IOException {
		String todo = "pick 1111111 \n" + "pick 2222222 Commit 2\n"
				+ "# Comment line at end\n";
		write(getTodoFile()

		List<RebaseTodoLine> steps = db.readRebaseTodo(GIT_REBASE_TODO
		assertEquals(2
		assertEquals("1111111"
		assertEquals("2222222"
	}

