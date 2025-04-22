
	public void testCheckoutCommit() {
		try {
			git.checkout().setName(initialCommit.name()).call();
			assertEquals("[Test.txt
					indexState(CONTENT));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
