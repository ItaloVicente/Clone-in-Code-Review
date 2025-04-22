	@Test
	public void testSortUsingComparator() {
		final IntList list = new IntList();
		list.add(-3);
		list.add(-2);
		list.add(0);
		list.add(1);
		list.add(4);
		list.add(1);
		list.sort(Comparator.comparingInt(Math::abs));
		int[] expected = { 0
		for (int i = 0; i < list.size(); i++) {
			assertEquals(expected[i]
		}
	}

