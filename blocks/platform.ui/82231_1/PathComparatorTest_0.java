	@Test
	public void checkInvariant() {
		Path ab = new Path("a/b");
		Path abc = new Path("a/b/c");
		Path ac = new Path("a/c");
		Path acb = new Path("a/c/b");
		Assert.assertTrue(COMPARATOR.compare(ab, abc) < 0);
		Assert.assertTrue(COMPARATOR.compare(abc, ac) < 0);
		Assert.assertTrue(COMPARATOR.compare(ac, acb) < 0);
	}

