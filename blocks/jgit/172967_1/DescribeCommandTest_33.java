	@Test
	public void testDescribeUseAllRefsMaster() throws Exception {
		final ObjectId c1 = modify("aaa");
		tag("t1");

		if (useAnnotatedTags || describeUseAllTags) {
			assertEquals("t1"
		} else {
			assertEquals(null
		}
		assertEquals("heads/master"
	}

	@Test
	public void testDescribeUseAllRefsBranch() throws Exception {
		final ObjectId c1 = modify("aaa");
		modify("bbb");

		branch("b"
		final ObjectId c3 = modify("ccc");
		tag("t1");

		if (!useAnnotatedTags && !describeUseAllTags) {
			assertEquals(null
		} else {
			assertEquals("t1"
		}
		assertEquals("heads/b"
	}

