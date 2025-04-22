
	public void testIsId() {
		assertFalse(AbbreviatedObjectId.isId(""));
		assertFalse(AbbreviatedObjectId.isId("a"));

		assertFalse(AbbreviatedObjectId.isId(ObjectId.fromString(
				"7b6e8067ec86acef9a4184b43210d583b6d2f99a").name()
				+ "0"));
		assertFalse(AbbreviatedObjectId.isId(ObjectId.fromString(
				"7b6e8067ec86acef9a4184b43210d583b6d2f99a").name()
				+ "c0ffee"));

		assertFalse(AbbreviatedObjectId.isId("01notahexstring"));

		assertTrue(AbbreviatedObjectId.isId("ab"));
		assertTrue(AbbreviatedObjectId.isId("abc"));
		assertTrue(AbbreviatedObjectId.isId("abcd"));
		assertTrue(AbbreviatedObjectId.isId("abcd0"));
		assertTrue(AbbreviatedObjectId.isId("abcd09"));
		assertTrue(AbbreviatedObjectId.isId(ObjectId.fromString(
				"7b6e8067ec86acef9a4184b43210d583b6d2f99a").name()));
	}
