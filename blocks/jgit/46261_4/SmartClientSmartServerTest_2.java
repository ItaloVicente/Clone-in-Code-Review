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

		String remoteUri = redirectURI.toString();
		int i = remoteUri.lastIndexOf('/');
		remoteUri = remoteUri.substring(0
				+ remoteUri.substring(i);
		URIish cloneFrom = new URIish(remoteUri);
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

		String remoteUri = redirectURI.toString();
		int i = remoteUri.lastIndexOf('/');
		remoteUri = remoteUri.substring(0
				+ remoteUri.substring(i);
		URIish cloneFrom = new URIish(remoteUri);
		try (Transport t = Transport.open(dst
			t.fetch(NullProgressMonitor.INSTANCE
			fail("Should have failed (redirect loop)");
		} catch (TransportException e) {
		}
	}

	@Test
	public void testInitialClone_RedirectForbidden() throws Exception {
		FileBasedConfig userConfig = SystemReader.getInstance()
				.openUserConfig(null
		userConfig.setString("http"
		userConfig.save();

		Repository dst = createBareRepository();
		assertFalse(dst.hasObject(A_txt));

		try (Transport t = Transport.open(dst
			t.fetch(NullProgressMonitor.INSTANCE
			fail("Should have failed (redirects forbidden)");
		} catch (TransportException e) {
			assertTrue(
					e.getMessage().contains("http.followRedirects is false"));
		}
	}

