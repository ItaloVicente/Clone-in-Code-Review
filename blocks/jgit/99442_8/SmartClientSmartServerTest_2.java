	@Test
	public void testInitialClone_WithAuthentication() throws Exception {
		Repository dst = createBareRepository();
		assertFalse(dst.hasObject(A_txt));

		try (Transport t = Transport.open(dst
			t.setCredentialsProvider(testCredentials);
			t.fetch(NullProgressMonitor.INSTANCE
		}

		assertTrue(dst.hasObject(A_txt));
		assertEquals(B
		fsck(dst

		List<AccessEvent> requests = getRequests();
		assertEquals(3

		AccessEvent info = requests.get(0);
		assertEquals("GET"
		assertEquals(401

		info = requests.get(1);
		assertEquals("GET"
		assertEquals(join(authURI
		assertEquals(1
		assertEquals("git-upload-pack"
		assertEquals(200
		assertEquals("application/x-git-upload-pack-advertisement"
				info.getResponseHeader(HDR_CONTENT_TYPE));
		assertEquals("gzip"

		AccessEvent service = requests.get(2);
		assertEquals("POST"
		assertEquals(join(authURI
		assertEquals(0
		assertNotNull("has content-length"
				service.getRequestHeader(HDR_CONTENT_LENGTH));
		assertNull("not chunked"
				service.getRequestHeader(HDR_TRANSFER_ENCODING));

		assertEquals(200
		assertEquals("application/x-git-upload-pack-result"
				service.getResponseHeader(HDR_CONTENT_TYPE));
	}

	@Test
	public void testInitialClone_WithAuthenticationNone() throws Exception {
		Repository dst = createBareRepository();
		assertFalse(dst.hasObject(A_txt));

		try (Transport t = Transport.open(dst
			t.fetch(NullProgressMonitor.INSTANCE
			fail("Should not have succeeded -- no authentication");
		} catch (TransportException e) {
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
	public void testInitialClone_WithAuthenticationWrong() throws Exception {
		Repository dst = createBareRepository();
		assertFalse(dst.hasObject(A_txt));

		try (Transport t = Transport.open(dst
			t.setCredentialsProvider(new UsernamePasswordCredentialsProvider(
					AppServer.username
			t.fetch(NullProgressMonitor.INSTANCE
			fail("Should not have succeeded -- wrong password");
		} catch (TransportException e) {
			String msg = e.getMessage();
			assertTrue("Unexpected exception message: " + msg
					msg.contains("auth"));
		}
		List<AccessEvent> requests = getRequests();
		assertEquals(4

		for (AccessEvent event : requests) {
			assertEquals("GET"
			assertEquals(401
		}
	}

	@Test
	public void testInitialClone_WithAuthenticationAfterRedirect()
			throws Exception {
		Repository dst = createBareRepository();
		assertFalse(dst.hasObject(A_txt));

		URIish cloneFrom = extendPath(redirectURI
		CredentialsProvider uriSpecificCredentialsProvider = new UsernamePasswordCredentialsProvider(
				"unknown"
			@Override
			public boolean get(URIish uri
					throws UnsupportedCredentialItem {
				if (uri.getPath().startsWith("/auth")) {
					return testCredentials.get(uri
				}
				return super.get(uri
			}
		};
		try (Transport t = Transport.open(dst
			t.setCredentialsProvider(uriSpecificCredentialsProvider);
			t.fetch(NullProgressMonitor.INSTANCE
		}

		assertTrue(dst.hasObject(A_txt));
		assertEquals(B
		fsck(dst

		List<AccessEvent> requests = getRequests();
		assertEquals(4

		AccessEvent redirct = requests.get(0);
		assertEquals("GET"
		assertEquals(join(cloneFrom
		assertEquals(301

		AccessEvent info = requests.get(1);
		assertEquals("GET"
		assertEquals(join(authURI
		assertEquals(401

		info = requests.get(2);
		assertEquals("GET"
		assertEquals(join(authURI
		assertEquals(1
		assertEquals("git-upload-pack"
		assertEquals(200
		assertEquals("application/x-git-upload-pack-advertisement"
				info.getResponseHeader(HDR_CONTENT_TYPE));
		assertEquals("gzip"

		AccessEvent service = requests.get(3);
		assertEquals("POST"
		assertEquals(join(authURI
		assertEquals(0
		assertNotNull("has content-length"
				service.getRequestHeader(HDR_CONTENT_LENGTH));
		assertNull("not chunked"
				service.getRequestHeader(HDR_TRANSFER_ENCODING));

		assertEquals(200
		assertEquals("application/x-git-upload-pack-result"
				service.getResponseHeader(HDR_CONTENT_TYPE));
	}

	@Test
	public void testInitialClone_WithAuthenticationOnPostOnly()
			throws Exception {
		Repository dst = createBareRepository();
		assertFalse(dst.hasObject(A_txt));

		try (Transport t = Transport.open(dst
			t.setCredentialsProvider(testCredentials);
			t.fetch(NullProgressMonitor.INSTANCE
		}

		assertTrue(dst.hasObject(A_txt));
		assertEquals(B
		fsck(dst

		List<AccessEvent> requests = getRequests();
		assertEquals(3

		AccessEvent info = requests.get(0);
		assertEquals("GET"
		assertEquals(join(authOnPostURI
		assertEquals(1
		assertEquals("git-upload-pack"
		assertEquals(200
		assertEquals("application/x-git-upload-pack-advertisement"
				info.getResponseHeader(HDR_CONTENT_TYPE));
		assertEquals("gzip"

		AccessEvent service = requests.get(1);
		assertEquals("POST"
		assertEquals(join(authOnPostURI
		assertEquals(401

		service = requests.get(2);
		assertEquals("POST"
		assertEquals(join(authOnPostURI
		assertEquals(0
		assertNotNull("has content-length"
				service.getRequestHeader(HDR_CONTENT_LENGTH));
		assertNull("not chunked"
				service.getRequestHeader(HDR_TRANSFER_ENCODING));

		assertEquals(200
		assertEquals("application/x-git-upload-pack-result"
				service.getResponseHeader(HDR_CONTENT_TYPE));
	}

