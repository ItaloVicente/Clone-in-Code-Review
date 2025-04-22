	public void testCheckout() throws Exception {
		git.checkout().setName("test").call();
		assertEquals("[Test.txt
				indexState(CONTENT));
		Ref result = git.checkout().setName("master").call();
		assertEquals("[Test.txt
				indexState(CONTENT));
		assertEquals("refs/heads/master"
		assertEquals("refs/heads/master"
