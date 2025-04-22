		assertFetchRequests(requests
	}

	@Test
	public void testInitialClone_WithPreAuthentication() throws Exception {
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			((TransportHttp) t).setPreemptiveBasicAuthentication(
					AppServer.username
			t.fetch(NullProgressMonitor.INSTANCE
			assertTrue(dst.getObjectDatabase().has(A_txt));
			assertEquals(B
			fsck(dst
		}

		List<AccessEvent> requests = getRequests();
		assertEquals(enableProtocolV2 ? 3 : 2

		assertFetchRequests(requests
	}

	@Test
	public void testInitialClone_WithPreAuthenticationCleared()
			throws Exception {
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			((TransportHttp) t).setPreemptiveBasicAuthentication(
					AppServer.username
			((TransportHttp) t).setPreemptiveBasicAuthentication(null
			t.setCredentialsProvider(testCredentials);
			t.fetch(NullProgressMonitor.INSTANCE
			assertTrue(dst.getObjectDatabase().has(A_txt));
			assertEquals(B
			fsck(dst
		}

		List<AccessEvent> requests = getRequests();
		assertEquals(enableProtocolV2 ? 4 : 3

		AccessEvent info = requests.get(0);
