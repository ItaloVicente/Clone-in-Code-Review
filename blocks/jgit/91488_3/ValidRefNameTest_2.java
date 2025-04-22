	@Test
	public void testNormalizeWithSlashes() {
		assertNormalized("foo/bar/baz"
		assertNormalized("foo/bar.lock/baz.lock"
		assertNormalized("foo/.git/.git~1/bar"
		assertNormalized(".foo/aux/con/com3.txt/com0/prn/lpt1"
				"foo/+aux/+con/+com3.txt/com0/+prn/+lpt1");
	}
