	@Test
	public void testObjectId_objectid_invalid_explicit_parent() throws IOException {
		assertEquals("42e4e7c5e507e113ebbb7801b16b52cf867b7ce1"
		assertNull(db.resolve("6462e7d8024396b14d7651e2ec11e2bbf07a05c4^2"));
		assertEquals("42e4e7c5e507e113ebbb7801b16b52cf867b7ce1"
		assertNull(db.resolve("42e4e7c5e507e113ebbb7801b16b52cf867b7ce1^1"));
		assertNull(db.resolve("42e4e7c5e507e113ebbb7801b16b52cf867b7ce1^2"));
	}

