		assertTrue(filter.include(fakeWalk("a")));
		assertTrue(filter.include(fakeWalk("b/c")));
		assertTrue(filter.include(fakeWalk("c/d/e")));
		assertTrue(filter.include(fakeWalk("c/d/f")));
		assertTrue(filter.include(fakeWalk("d/e/f/g")));
		assertTrue(filter.include(fakeWalk("d/e/f/g.x")));
