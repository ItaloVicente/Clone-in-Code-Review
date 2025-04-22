	public void testPushToOriginPushNode() throws Exception {
		testPushToOrigin(false);
	}
	
	@Test
	public void testPushToOriginRemoteNode() throws Exception {
		testPushToOrigin(true);
	}

	private void testPushToOrigin(boolean useRemote) throws Exception {
