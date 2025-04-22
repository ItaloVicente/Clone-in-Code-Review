	@Test
	public void sortingShouldIgnoreCase() {
		assertSortedLike("a", "b", "z");
		assertSortedLike("a", "B", "c", "D");
	}

