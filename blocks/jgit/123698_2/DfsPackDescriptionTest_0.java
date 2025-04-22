	@Test
	public void objectLookupComparatorCustomPackSourceComparator()
			throws Exception {
		DfsPackDescription a = create(GC);

		DfsPackDescription b = create(COMPACT);

		assertComparesLessThan(DfsPackDescription.objectLookupComparator()
		assertComparesLessThan(
				DfsPackDescription.objectLookupComparator(
					new PackSource.ComparatorBuilder()
						.add(GC)
						.add(INSERT
						.add(COMPACT)
						.build())
				a
	}

