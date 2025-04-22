
	@Test
	public void testCheckoutRepository() throws Exception {
		CheckoutCommand co = git.checkout();
		File test = writeTrashFile(FILE1
		File test2 = writeTrashFile(FILE2
		co.setStartPoint("HEAD~2").setAllPaths(true).call();
		assertEquals("1"
		assertEquals("a"
	}
