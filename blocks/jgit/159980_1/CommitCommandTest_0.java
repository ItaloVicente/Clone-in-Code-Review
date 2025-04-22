	@Test
	public void commitWithAutoCrlfAndNonNormalizedIndex() throws Exception {
		nonNormalizedIndexTest(false);
	}

	@Test
	public void commitExecutableWithAutoCrlfAndNonNormalizedIndex()
			throws Exception {
		assumeTrue(FS.DETECTED.supportsExecute());
		nonNormalizedIndexTest(true);
	}

