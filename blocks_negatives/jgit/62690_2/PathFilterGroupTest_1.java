		assertFalse(filter.include(fakeWalk("a+")));
		assertFalse(filter.include(fakeWalk("b+/c")));
		assertFalse(filter.include(fakeWalk("c+/d/e")));
		assertFalse(filter.include(fakeWalk("c+/d/f")));
		assertFalse(filter.include(fakeWalk("c/d.a")));
		assertFalse(filter.include(fakeWalk("d+/e/f/g")));
