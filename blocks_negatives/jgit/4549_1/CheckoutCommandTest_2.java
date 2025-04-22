	public void testCreateBranchOnCheckout() throws IOException {
		try {
			git.checkout().setCreateBranch(true).setName("test2").call();
		} catch (Exception e) {
			fail(e.getMessage());
		}
