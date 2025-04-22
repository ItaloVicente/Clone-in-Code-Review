	@Test
	public void testLocalTransportFetchWithoutLocalRepository()
			throws Exception {
		transport = Transport.open(uri);
		FetchConnection fetchConnection = transport.openFetch();
		try {
			Ref head = fetchConnection.getRef(Constants.HEAD);
			assertNotNull(head);
		} finally {
			fetchConnection.close();
		}
	}

