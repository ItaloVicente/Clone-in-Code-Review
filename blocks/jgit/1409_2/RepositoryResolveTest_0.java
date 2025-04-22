
	public void testParseLookupPath() throws IOException {
		ObjectId b2_txt = id("10da5895682013006950e7da534b705252b03be6");
		ObjectId b3_b2_txt = id("e6bfff5c1d0f0ecd501552b43a1e13d8008abc31");
		ObjectId b_root = id("acd0220f06f7e4db50ea5ba242f0dfed297b27af");
		ObjectId master_txt = id("82b1d08466e9505f8666b778744f9a3471a70c81");

		assertEquals(b2_txt
		assertEquals(b_root
		assertEquals(master_txt
		assertEquals(b3_b2_txt

		assertNull("no FOO"
		assertNull("no b/FOO"
		assertNull("no b/FOO"
		assertNull("no not-a-branch:"
	}

	private static ObjectId id(String name) {
		return ObjectId.fromString(name);
	}
