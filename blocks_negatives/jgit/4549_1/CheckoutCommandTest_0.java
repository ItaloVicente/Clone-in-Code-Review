	public void testSimpleCheckout() {
		try {
			git.checkout().setName("test").call();
		} catch (Exception e) {
			fail(e.getMessage());
		}
