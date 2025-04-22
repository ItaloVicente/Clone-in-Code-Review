	public void testCheckoutCommit() {
		try {
			Ref result = git.checkout().setName(initialCommit.name()).call();
			assertEquals("[Test.txt, mode:100644, content:Hello world]",
					indexState(CONTENT));
			assertNull(result);
			assertEquals(initialCommit.name(), git.getRepository()
					.getFullBranch());
		} catch (Exception e) {
			fail(e.getMessage());
		}
