	public void testWriteHEAD() throws Exception {
		byte[] bundle = makeBundle("HEAD"
				"42e4e7c5e507e113ebbb7801b16b52cf867b7ce1"

		Repository newRepo = createBareRepository();
		FetchResult fetchResult = fetchFromBundle(newRepo
		Ref advertisedRef = fetchResult.getAdvertisedRef("HEAD");

		assertEquals("42e4e7c5e507e113ebbb7801b16b52cf867b7ce1"
				.getObjectId().name());
	}

	@Test
	public void testIncrementalBundle() throws Exception {
