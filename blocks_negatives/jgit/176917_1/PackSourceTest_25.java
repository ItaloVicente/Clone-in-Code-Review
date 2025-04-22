
		assertEquals(-1, DEFAULT_COMPARATOR.compare(GC_REST, GC_TXN));
		assertEquals(1, DEFAULT_COMPARATOR.compare(GC_TXN, GC_REST));

		assertEquals(-1, DEFAULT_COMPARATOR.compare(GC_TXN, UNREACHABLE_GARBAGE));
		assertEquals(1, DEFAULT_COMPARATOR.compare(UNREACHABLE_GARBAGE, GC_TXN));
