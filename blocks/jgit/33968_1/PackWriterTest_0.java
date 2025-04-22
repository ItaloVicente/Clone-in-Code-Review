	@Test
	public void testIgnoreNonExistingObjectsWithBitmaps() throws IOException
			ParseException {
		final ObjectId nonExisting = ObjectId
				.fromString("0000000000000000000000000000000000000001");
		new GC(db).gc();
		createVerifyOpenPack(EMPTY_SET_OBJECT
				Collections.singleton(nonExisting)
	}

