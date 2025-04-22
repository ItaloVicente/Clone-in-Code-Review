	@Test public void shouldReturnNotEqualForDifferetnRepoId() throws Exception {
		GitModelCacheTree left = crateCacheTree(zeroId(), getTreeLocation());
		GitModelCacheTree right = crateCacheTree(
				fromString("4c879313cd1332e594b1ad20b1485bdff9533034"),
				getTreeLocation());

		boolean actual = left.equals(right);

		assertFalse(actual);
	}

