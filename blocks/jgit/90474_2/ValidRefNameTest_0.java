
	@Test
	public void testNormalizeAlreadyValidRefName() {
		assertEquals(true
				Repository.normalizeBranchName("refs/heads/m.a.s.t.e.r")
						.equals("refs/heads/m.a.s.t.e.r"));
	}

	@Test
	public void testNormalizeTrimmedUnicodeAlreadyValidRefName() {
		assertEquals(true
				Repository.normalizeBranchName(" \u00e5ngstr\u00f6m\t")
						.equals("\u00e5ngstr\u00f6m"));
	}
