		List<RebaseTodoLine> steps = db.readRebaseTodo(GIT_REBASE_TODO

		assertEquals(2
		assertEquals("1111111"
		assertEquals("2222222"
		assertEquals(Action.REWORD
	}

	@Test
	public void testEmptyRebaseTodo() throws Exception {
		write(getTodoFile()
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void testOnlyCommentRebaseTodo() throws Exception {
		write(getTodoFile()
		assertEquals(2
		assertEquals(0
		write(getTodoFile()
		assertEquals(2
		assertEquals(0
		write(getTodoFile()
		assertEquals(4
		assertEquals(0
	}

	@Test
	public void testLeadingSpacesRebaseTodo() throws Exception {
		String todo =	"  \t\t pick 1111111 Commit 1\n"
					+ "\t\n"
					+ "\treword 2222222 Commit 2\n";
		write(getTodoFile()

		List<RebaseTodoLine> steps = db.readRebaseTodo(GIT_REBASE_TODO
