	@Test
	public void testInitialClone_PreAuthenticationTooLate() throws Exception {
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			((TransportHttp) t).setPreemptiveBasicAuthentication(
					AppServer.username
			t.fetch(NullProgressMonitor.INSTANCE
			assertTrue(dst.getObjectDatabase().has(A_txt));
			assertEquals(B
			fsck(dst
			List<AccessEvent> requests = getRequests();
			assertEquals(2
			assertFetchRequests(requests
			assertThrows(IllegalStateException.class
					() -> ((TransportHttp) t).setPreemptiveBasicAuthentication(
							AppServer.username
			assertThrows(IllegalStateException.class
					.setPreemptiveBasicAuthentication(null
		}
	}

	@Test
	public void testInitialClone_WithWrongPreAuthenticationAndCredentialProvider()
			throws Exception {
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			((TransportHttp) t).setPreemptiveBasicAuthentication(
					AppServer.username
			t.setCredentialsProvider(testCredentials);
			t.fetch(NullProgressMonitor.INSTANCE
			assertTrue(dst.getObjectDatabase().has(A_txt));
			assertEquals(B
			fsck(dst
		}

		List<AccessEvent> requests = getRequests();
		assertEquals(3

		AccessEvent info = requests.get(0);
		assertEquals("GET"
		assertEquals(401

		assertFetchRequests(requests
	}

	@Test
	public void testInitialClone_WithWrongPreAuthentication() throws Exception {
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			((TransportHttp) t).setPreemptiveBasicAuthentication(
					AppServer.username
			TransportException e = assertThrows(TransportException.class
					() -> t.fetch(NullProgressMonitor.INSTANCE
							mirror(master)));
			String msg = e.getMessage();
			assertTrue("Unexpected exception message: " + msg
					msg.contains("no CredentialsProvider"));
		}
		List<AccessEvent> requests = getRequests();
		assertEquals(1

		AccessEvent info = requests.get(0);
		assertEquals("GET"
		assertEquals(401
	}

	@Test
	public void testInitialClone_WithUserInfo() throws Exception {
		URIish withUserInfo = authURI.setUser(AppServer.username)
				.setPass(AppServer.password);
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			t.fetch(NullProgressMonitor.INSTANCE
			assertTrue(dst.getObjectDatabase().has(A_txt));
			assertEquals(B
			fsck(dst
		}

		List<AccessEvent> requests = getRequests();
		assertEquals(2

		assertFetchRequests(requests
	}

	@Test
	public void testInitialClone_PreAuthOverridesUserInfo() throws Exception {
		URIish withUserInfo = authURI.setUser(AppServer.username)
				.setPass(AppServer.password + 'x');
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
		assertEquals(2

		assertFetchRequests(requests
