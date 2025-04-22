
		if (useAnnotatedTags || describeUseAllTags) {
			assertEquals("t2-2-g119892b"
			assertEquals("t1-1-g0244e7f"
		} else {
			assertEquals(null
			assertEquals(null
		}
	}

	@Test
	public void t1AnnotatedDominatesT2lightweight() throws Exception {
		ObjectId c1 = modify("aaa");
		tag("t1"

		ObjectId c2 = modify("bbb");
		tag("t2"

		assertNameStartsWith(c2
		if (useAnnotatedTags && !describeUseAllTags) {
			assertEquals(
					"only annotated tag t1 expected to be used for describe"
					"t1-1-g3747db3"
		} else if (!useAnnotatedTags && !describeUseAllTags) {
			assertEquals("no commits to describe expected"
		} else {
			assertEquals("lightweight tag t2 expected in describe"
					describe(c2));
		}

		branch("b"

		ObjectId c3 = modify("ccc");
