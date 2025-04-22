	@Test
	public void testWritePack2SizeDeltaCompressVsNoDeltas() throws Exception {
		testWritePack2();
		final long sizePack2NoDeltas = os.size();
		tearDown();
		setUp();
		testWritePack2DeltaCompress();
		final long sizePack2DeltasRefs = os.size();

		assertTrue(sizePack2NoDeltas > sizePack2DeltasRefs);
	}

