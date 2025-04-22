	@Test
	public void globMatchWithSlashes() throws Exception {
		ObjectId c1 = modify("aaa");
		tag("a/b/version");
		ObjectId c2 = modify("bbb");
		tag("a/b/version2");
		if (useAnnotatedTags || describeUseAllTags) {
			assertEquals("a/b/version"
			assertEquals("a/b/version2"
		} else {
			assertNull(describe(c1));
			assertNull(describe(c1
			assertNull(describe(c2));
			assertNull(describe(c2
		}
	}

