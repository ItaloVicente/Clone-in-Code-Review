
	void testNormalize(String in
		URI got = ManifestParser.normalizeEmptyPath(URI.create(in));
		if (!got.toString().equals(want)) {
			Assert.fail(String.format("normalize(%s) = %s want %s"
		}
	}

	@Test
	public void normalizeEmptyPath() {
		testNormalize(""
		testNormalize("a/b"
	}
