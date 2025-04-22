
	void testNormalize(String in
		URI got = ManifestParser.normalizeEmptyPath(URI.create(in));
		if (!got.toString().equals(want)) {
			fail(String.format("normalize(%s) = %s want %s"
		}
	}

	@Test
	public void testNormalizeEmptyPath() {
		testNormalize(""
		testNormalize("a/b"
	}
