
	@Test
	public void testNormalizeBranchName() {

		assertEquals(true
				Repository.normalizeBranchName("Bug 12345 - Hello World!"
						.equals("Bug_12345-Hello_World"));

		assertEquals(true
				Repository.normalizeBranchName("Bug 12345 : Hello World!"
						.equals("Bug_12345-Hello_World"));

		assertEquals(true
				Repository.normalizeBranchName("Bug 12345 _ Hello World!"
						.equals("Bug_12345_Hello_World"));

		assertEquals(true
				.normalizeBranchName("Bug 12345   -       Hello World!"
				.equals("Bug_12345-Hello_World"));

		assertEquals(true
				.normalizeBranchName(" Bug 12345   -   Hello World! "
				.equals("Bug_12345-Hello_World_"));

		assertEquals(true
				.normalizeBranchName(" Bug 12345   -   Hello World!   "
				.equals("Bug_12345-Hello_World_"));

		assertEquals(true
				Repository
						.normalizeBranchName(
								"Bug 12345   -   Hello______ World!"
						.equals("Bug_12345-Hello_World"));

		assertEquals(true
				Repository
						.normalizeBranchName("_Bug 12345 - Hello World!"
						.equals("Bug_12345-Hello_World"));

		assertEquals(true
				Repository
						.normalizeBranchName(
								"Bug 12345 - Hello Wo!@#$%^&*(rld {@"
						.equals("Bug_12345-Hello_World_"));

		assertEquals(true
				.normalizeBranchName("Bug 1#$  2345 - Hello World"
				.equals("Bug_1_2345-Hello_World"));
	}
