		assertMatches(Sets.of("b/c"), fakeWalk("b"));
		assertMatches(Sets.of("c/d/e", "c/d/f"), fakeWalk("c/d"));
		assertMatches(Sets.of("c/d/e", "c/d/f"), fakeWalk("c"));
		assertMatches(Sets.of("d/e/f/g", "d/e/f/g.x"), fakeWalk("d/e/f"));
		assertMatches(Sets.of("d/e/f/g", "d/e/f/g.x"), fakeWalk("d/e"));
		assertMatches(Sets.of("d/e/f/g", "d/e/f/g.x"), fakeWalk("d"));
