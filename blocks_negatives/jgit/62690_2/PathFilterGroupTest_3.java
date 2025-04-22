		assertTrue(filter.include(fakeWalk("a/b")));
		assertTrue(filter.include(fakeWalk("b/c/d")));
		assertTrue(filter.include(fakeWalk("c/d/e/f")));
		assertTrue(filter.include(fakeWalk("c/d/f/g")));
		assertTrue(filter.include(fakeWalk("d/e/f/g/h")));
		assertTrue(filter.include(fakeWalk("d/e/f/g/y")));
		assertTrue(filter.include(fakeWalk("d/e/f/g.x/h")));
		assertFalse(filter.include(fakeWalk("d/e/f/g.y")));
