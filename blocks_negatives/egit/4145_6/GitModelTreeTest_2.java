	@Test public void shouldReturnNotEqualForDifferentBaseCommit()
			throws Exception {
		GitModelTree left = createModelTree(HEAD, getTreeLocation());
		GitModelTree right = createModelTree(HEAD + "~1", getTreeLocation());

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

