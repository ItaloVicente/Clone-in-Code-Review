	public void testFetchFromOriginFetchNode() throws Exception {
		testFetchFromOrigin(false);
	}
	
	@Test
	public void testFetchFromOriginRemoteNode() throws Exception {
		testFetchFromOrigin(true);
	}

	private void testFetchFromOrigin(boolean useRemote) throws Exception {
