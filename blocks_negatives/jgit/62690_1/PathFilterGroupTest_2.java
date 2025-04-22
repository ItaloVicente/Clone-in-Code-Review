		assertTrue(filter.include(fakeWalk("b")));
		assertTrue(filter.include(fakeWalk("c/d")));
		assertTrue(filter.include(fakeWalk("c/d")));
		assertTrue(filter.include(fakeWalk("c")));
		assertTrue(filter.include(fakeWalk("d/e/f")));
		assertTrue(filter.include(fakeWalk("d/e")));
		assertTrue(filter.include(fakeWalk("d")));
