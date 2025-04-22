	@Test
	public void testInitialClone_WithPreAuthenticationAndRedirect()
			throws Exception {
		URIish cloneFrom = extendPath(redirectURI
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
