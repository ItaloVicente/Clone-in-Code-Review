	public void testCheckout() {
		try {
			git.checkout().setName("test").call();
			assertEquals("[Test.txt, mode:100644, content:Some change]",
					indexState(CONTENT));
			Ref result = git.checkout().setName("master").call();
			assertEquals("[Test.txt, mode:100644, content:Hello world]",
					indexState(CONTENT));
			assertEquals("refs/heads/master", result.getName());
			assertEquals("refs/heads/master", git.getRepository()
					.getFullBranch());
		} catch (Exception e) {
			fail(e.getMessage());
		}
