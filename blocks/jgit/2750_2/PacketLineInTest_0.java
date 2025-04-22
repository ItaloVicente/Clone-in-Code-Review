	@Test
	public void testReadACK_ERR() throws IOException {
		init("001aERR want is not valid\n");
		try {
			in.readACK(new MutableObjectId());
			fail("incorrectly accepted ERR");
		} catch (PackProtocolException e) {
			assertEquals("want is not valid"
		}
	}

