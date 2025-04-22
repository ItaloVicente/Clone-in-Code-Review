	@AfterClass
	public static void shutdown() {
		perspective.activate();
	}

	@Test
	public void testNoChanges() throws Exception {
