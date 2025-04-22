		assertMatches(Sets.of("b/c")
		assertMatches(Sets.of("c/d/e"
		assertMatches(Sets.of("c/d/e"
		assertMatches(Sets.of("d/e/f/g"
				fakeWalkAtSubtree("d/e/f"));
		assertMatches(Sets.of("d/e/f/g"
				fakeWalkAtSubtree("d/e"));
		assertMatches(Sets.of("d/e/f/g"

		assertNoMatches(fakeWalk("b"));
		assertNoMatches(fakeWalk("c/d"));
		assertNoMatches(fakeWalk("c"));
		assertNoMatches(fakeWalk("d/e/f"));
		assertNoMatches(fakeWalk("d/e"));
		assertNoMatches(fakeWalk("d"));

