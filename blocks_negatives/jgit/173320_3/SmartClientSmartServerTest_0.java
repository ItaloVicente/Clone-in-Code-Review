	@Test
	public void testInitialClone_WithAuthentication() throws Exception {
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst, authURI)) {
			assertFalse(dst.getObjectDatabase().has(A_txt));
			t.setCredentialsProvider(testCredentials);
			t.fetch(NullProgressMonitor.INSTANCE, mirror(master));
			assertTrue(dst.getObjectDatabase().has(A_txt));
			assertEquals(B, dst.exactRef(master).getObjectId());
			fsck(dst, B);
		}

		List<AccessEvent> requests = getRequests();
		assertEquals(enableProtocolV2 ? 4 : 3, requests.size());

		AccessEvent info = requests.get(0);
		assertEquals("GET", info.getMethod());
		assertEquals(401, info.getStatus());

		info = requests.get(1);
