	public void testParseGitDescribeOutput() throws IOException {
		ObjectId exp = db.resolve("b");
		assertEquals(exp
		assertEquals(exp

		assertEquals(exp
		assertEquals(exp

		try {
			db.resolve("B-6-g7f82283^{blob}");
			fail("expected IncorrectObjectTypeException");
		} catch (IncorrectObjectTypeException badType) {
		}

		assertEquals(db.resolve("b^1")
		assertEquals(db.resolve("b~2")
	}
