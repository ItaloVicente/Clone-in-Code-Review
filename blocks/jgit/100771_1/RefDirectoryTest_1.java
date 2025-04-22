		testBatchRefUpdateNonFastForwardDoesNotDoExpensiveMergeCheck(false);
	}

	@Test
	public void testBatchRefUpdateNonFastForwardDoesNotDoExpensiveMergeCheckAtomic()
			throws IOException {
		testBatchRefUpdateNonFastForwardDoesNotDoExpensiveMergeCheck(true);
	}

	private void testBatchRefUpdateNonFastForwardDoesNotDoExpensiveMergeCheck(
			boolean atomic) throws IOException {
