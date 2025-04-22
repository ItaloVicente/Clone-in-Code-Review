	@Test
	public void t1AnnotatedDominatesT2leightweight() throws Exception {
		ObjectId c1 = modify("aaa");
		tag("t1"

		ObjectId c2 = modify("bbb");
		tag("t2"

		assertNameStartsWith(c2
		if (useAnnotatedTags) {
			assertEquals("t1-1-g3747db3"
		} else {
			assertEquals("t2"
		}

		branch("b"

		ObjectId c3 = modify("ccc");

		ObjectId c4 = merge(c2);

		assertNameStartsWith(c4
		if (describeUseAllTags) {
			assertEquals("t2-1-g119892b"
		} else {
			assertEquals("t1-3-g119892b"
		}

		assertNameStartsWith(c3
		assertEquals("t1-1-g0244e7f"
	}

