
	@Test
	public void testAndTreeFilter_getTreeFilters() throws Exception {
		TreeFilter a = PathFilter.create("a");
		TreeFilter b = PathFilter.create("b");
		TreeFilter c = PathFilter.create("c");

		TreeFilter andFilters = AndTreeFilter.create(a
		assertTrue(andFilters instanceof AndTreeFilter);
		TreeFilter[] result = ((AndTreeFilter) andFilters).getTreeFilters();

		assertArrayEquals(new TreeFilter[] { a

		TreeFilter[] list = new TreeFilter[] { a
		andFilters = AndTreeFilter.create(list);
		assertTrue(andFilters instanceof AndTreeFilter);
		result = ((AndTreeFilter) andFilters).getTreeFilters();
		assertArrayEquals(list
	}
