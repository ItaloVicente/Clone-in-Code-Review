	@Test
	public void testDescribeMultiMatch() throws Exception {
		ObjectId c1 = modify("aaa");
		tag("v1.0.0");
		tag("v1.1.1");
		ObjectId c2 = modify("bbb");

		assertEquals("v1.0.0"
		assertEquals("v1.0.0-1-g3747db3"

		assertEquals("v1.0.0"
		assertEquals("v1.1.1"
		assertEquals("v1.0.0-1-g3747db3"
		assertEquals("v1.1.1-1-g3747db3"

		assertEquals("v1.0.0"
		assertEquals("v1.1.1"
		assertEquals("v1.0.0-1-g3747db3"
		assertEquals("v1.1.1-1-g3747db3"
	}

