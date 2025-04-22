		AccessEvent service = requests.get(n++);
		assertEquals("POST"
		assertEquals(join(remoteURI
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
	public void testInitialClone_Redirect301Small() throws Exception {
		initialClone_Redirect(1
	}

	@Test
	public void testInitialClone_Redirect302Small() throws Exception {
		initialClone_Redirect(1
	}

	@Test
	public void testInitialClone_Redirect303Small() throws Exception {
		initialClone_Redirect(1
	}

	@Test
	public void testInitialClone_Redirect307Small() throws Exception {
		initialClone_Redirect(1
	}

	@Test
	public void testInitialClone_RedirectMultiple() throws Exception {
		initialClone_Redirect(4
	}

	@Test
	public void testInitialClone_RedirectMax() throws Exception {
		FileBasedConfig userConfig = SystemReader.getInstance()
				.openUserConfig(null
		userConfig.setInt("http"
		userConfig.save();
		initialClone_Redirect(4
	}

	@Test
	public void testInitialClone_RedirectTooOften() throws Exception {
		FileBasedConfig userConfig = SystemReader.getInstance()
				.openUserConfig(null
		userConfig.setInt("http"
		userConfig.save();
		Repository dst = createBareRepository();
		assertFalse(dst.hasObject(A_txt));

		URIish cloneFrom = extendPath(redirectURI
		String remoteUri = cloneFrom.toString();
		try (Transport t = Transport.open(dst
			t.fetch(NullProgressMonitor.INSTANCE
			fail("Should have failed (too many redirects)");
		} catch (TransportException e) {
			String expectedMessageBegin = MessageFormat.format(
					JGitText.get().redirectLimitExceeded
					remoteUri.replace("/4/"
			String message = e.getMessage();
			if (message.length() > expectedMessageBegin.length()) {
				message = message.substring(0
			}
			assertEquals(expectedMessageBegin
		}
	}

	@Test
	public void testInitialClone_RedirectLoop() throws Exception {
		Repository dst = createBareRepository();
		assertFalse(dst.hasObject(A_txt));

		URIish cloneFrom = extendPath(redirectURI
		try (Transport t = Transport.open(dst
			t.fetch(NullProgressMonitor.INSTANCE
			fail("Should have failed (redirect loop)");
		} catch (TransportException e) {
			assertTrue(e.getMessage().contains("redirected more than"));
		}
	}

	@Test
	public void testInitialClone_RedirectOnPostAllowed() throws Exception {
		FileBasedConfig userConfig = SystemReader.getInstance()
				.openUserConfig(null
		userConfig.setString("http"
		userConfig.save();
		Repository dst = createBareRepository();
		assertFalse(dst.hasObject(A_txt));

		URIish cloneFrom = extendPath(remoteURI
		try (Transport t = Transport.open(dst
			t.fetch(NullProgressMonitor.INSTANCE
		}

		assertTrue(dst.hasObject(A_txt));
		assertEquals(B
		fsck(dst

		List<AccessEvent> requests = getRequests();
		assertEquals(3

		AccessEvent info = requests.get(0);
		assertEquals("GET"
		assertEquals(join(cloneFrom
		assertEquals(1
		assertEquals("git-upload-pack"
		assertEquals(200
		assertEquals("application/x-git-upload-pack-advertisement"
				info.getResponseHeader(HDR_CONTENT_TYPE));
		assertEquals("gzip"

		AccessEvent redirect = requests.get(1);
		assertEquals("POST"
		assertEquals(301
