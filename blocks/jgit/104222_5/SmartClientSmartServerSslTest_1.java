	@Test
	public void testInitialClone_SslFailure() throws Exception {
		Repository dst = createBareRepository();
		assertFalse(dst.hasObject(A_txt));

		try (Transport t = Transport.open(dst
			t.setCredentialsProvider(
					new UsernamePasswordCredentialsProvider("any"
			t.fetch(NullProgressMonitor.INSTANCE
			fail("Should have failed (SSL certificate not trusted)");
		} catch (TransportException e) {
			assertTrue(e.getMessage().contains("Secure connection"));
		}
	}

