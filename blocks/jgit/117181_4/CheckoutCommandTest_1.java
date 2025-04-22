	@Test
	public void testCheckoutForced() throws Exception {
		writeTrashFile("Test.txt"
		try {
			git.checkout().setName("master").call().getObjectId();
			fail("Expected CheckoutConflictException didn't occur");
		} catch (CheckoutConflictException e) {
		}
		assertEquals(initialCommit.getId()
				.setForced(true).call().getObjectId());
	}

