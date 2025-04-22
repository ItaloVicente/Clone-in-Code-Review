	@Test
	public void testInitialClone_RedirectOnPostForbidden() throws Exception {
		Repository dst = createBareRepository();
		assertFalse(dst.hasObject(A_txt));

		URIish cloneFrom = extendPath(remoteURI
		try (Transport t = Transport.open(dst
			t.fetch(NullProgressMonitor.INSTANCE
			fail("Should have failed (redirect on POST)");
		} catch (TransportException e) {
			assertTrue(e.getMessage().contains("301"));
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

