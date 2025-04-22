		assertNoMatches(fakeWalk("a+"));
		assertNoMatches(fakeWalk("b+/c"));
		assertNoMatches(fakeWalk("c+/d/e"));
		assertNoMatches(fakeWalk("c+/d/f"));
		assertNoMatches(fakeWalk("c/d.a"));
		assertNoMatches(fakeWalk("d+/e/f/g"));
