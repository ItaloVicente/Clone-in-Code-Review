	@Test
	public void testNormalizeWithSlashes() {
		assertNormalized("foo/bar/baz"
		assertNormalized("foo/bar.lock/baz.lock"
		assertNormalized("foo/.git/.git~1/bar"
		assertNormalized(".foo/aux/con/com3.txt/com0/prn/lpt1"
				"foo/+aux/+con/+com3.txt/com0/+prn/+lpt1");
	}

	@Test
	public void testNormalizeWithUnicode() {
				"f\u00f6\u00f6/b\u00e0r/b\u00e9-z");
				"\u5165\u53e3_entrance;/\u51fa\u53e3_ex-it");
