
	public void testCheckoutCommit() {
		try {
			Ref result = git.checkout().setName(initialCommit.name()).call();
			assertEquals("[Test.txt
					indexState(CONTENT));
			assertNull(result);
			assertEquals(initialCommit.name()
					.getFullBranch());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
