	public void testMaliciousPathEmptyUnix() throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setUnix();
		testMaliciousPathBadFirstCheckout(""
	}

	@Test
	public void testMaliciousPathEmptyWindows() throws Exception {
		((MockSystemReader) SystemReader.getInstance()).setWindows();
