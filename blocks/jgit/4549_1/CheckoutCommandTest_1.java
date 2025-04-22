	public void testCheckoutCommit() throws Exception {
		Ref result = git.checkout().setName(initialCommit.name()).call();
		assertEquals("[Test.txt
				indexState(CONTENT));
		assertNull(result);
		assertEquals(initialCommit.name()
