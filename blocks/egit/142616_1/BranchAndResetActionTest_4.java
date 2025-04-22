	@Test
	public void testCheckoutWithConflictsAndReset() throws Exception {
		checkoutWithConflictsAndReset(false);
	}

	@Test
	public void testCheckoutWithStagedConflictsAndReset() throws Exception {
		checkoutWithConflictsAndReset(true);
	}

