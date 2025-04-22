	@Test
	public void testRebaseShouldTryToParseValidLineMarkedAsComment()
			throws IOException {
		String todo = "# pick 1111111 Valid line commented out with space\n"
				+ "#edit 2222222 Valid line commented out without space\n"
				+ "# pick invalidLine Comment line at end\n";
		write(getTodoFile()

		List<RebaseTodoLine> steps = db.readRebaseTodo(GIT_REBASE_TODO
		assertEquals(3

		RebaseTodoLine firstLine = steps.get(0);

		assertEquals("1111111"
		assertEquals("Valid line commented out with space"
				firstLine.getShortMessage());
		assertEquals("comment"

		try {
			firstLine.setAction(Action.PICK);
			assertEquals("1111111"
			assertEquals("pick"
		} catch (Exception e) {
			fail("Valid parsable RebaseTodoLine that has been commented out should allow to change the action
		}

		assertEquals("2222222"
		assertEquals("comment"

		assertEquals(null
		assertEquals(null
		assertEquals("comment"
		assertEquals("# pick invalidLine Comment line at end"
				.getComment());
		try {
			steps.get(2).setAction(Action.PICK);
			fail("A comment RebaseTodoLine that doesn't contain a valid parsable line should fail
		} catch (Exception e) {
		}

	}

	@SuppressWarnings("unused")
	@Test
	public void testRebaseTodoLineSetComment() throws Exception {
		try {
			new RebaseTodoLine("This is a invalid comment");
			fail("Constructing a comment line with invalid comment string should fail
		} catch (JGitInternalException e) {
		}
		RebaseTodoLine validCommentLine = new RebaseTodoLine(
				"# This is a comment");
		assertEquals(Action.COMMENT
		assertEquals("# This is a comment"

		RebaseTodoLine actionLineToBeChanged = new RebaseTodoLine(Action.EDIT
				AbbreviatedObjectId.fromString("1111111")
		assertEquals(null

		try {
			actionLineToBeChanged.setComment("invalid comment");
			fail("Setting a invalid comment string should fail but doesn't");
		} catch (JGitInternalException e) {
			assertEquals(null
		}

		actionLineToBeChanged.setComment("# valid comment");
		assertEquals("# valid comment"
		try {
			actionLineToBeChanged.setComment("invalid comment");
			fail("Setting a invalid comment string should fail but doesn't");
		} catch (JGitInternalException e) {
			assertEquals("# valid comment"
		}
		try {
			actionLineToBeChanged.setComment("# line1 \n line2");
			actionLineToBeChanged.setComment("line1 \n line2");
			actionLineToBeChanged.setComment("\n");
			actionLineToBeChanged.setComment("# line1 \r line2");
			actionLineToBeChanged.setComment("line1 \r line2");
			actionLineToBeChanged.setComment("\r");
			actionLineToBeChanged.setComment("# line1 \n\r line2");
			actionLineToBeChanged.setComment("line1 \n\r line2");
			actionLineToBeChanged.setComment("\n\r");
			fail("Setting a multiline comment string should fail but doesn't");
		} catch (JGitInternalException e) {
		}
		actionLineToBeChanged.setComment("# valid comment");
		assertEquals("# valid comment"

		actionLineToBeChanged.setComment("# \t \t valid comment");
		assertEquals("# \t \t valid comment"
				actionLineToBeChanged.getComment());

		actionLineToBeChanged.setComment("#       ");
		assertEquals("#       "

		actionLineToBeChanged.setComment("");
		assertEquals(""

		actionLineToBeChanged.setComment("  ");
		assertEquals("  "

		actionLineToBeChanged.setComment("\t\t");
		assertEquals("\t\t"

		actionLineToBeChanged.setComment(null);
		assertEquals(null
	}

