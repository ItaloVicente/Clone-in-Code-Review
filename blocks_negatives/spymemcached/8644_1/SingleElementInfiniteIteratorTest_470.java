	public void testHasNextAndNext() {
		for (int i=0; i<100; ++i) {
			assertTrue(iterator.hasNext());
			assertSame(CONSTANT, iterator.next());
		}
	}
