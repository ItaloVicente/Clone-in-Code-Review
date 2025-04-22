	@Test
	public void testTimeoutExpired() throws Exception {
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			t.setTimeout(1);
			TransportException expected = assertThrows(TransportException.class
					() -> t.fetch(NullProgressMonitor.INSTANCE
							mirror(master)));
			assertTrue("Unexpected exception message: " + expected.toString()
					expected.getMessage().contains("time"));
		}
	}

	@Test
	public void testTimeoutExpiredWithAuth() throws Exception {
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			t.setTimeout(1);
			t.setCredentialsProvider(testCredentials);
			TransportException expected = assertThrows(TransportException.class
					() -> t.fetch(NullProgressMonitor.INSTANCE
							mirror(master)));
			assertTrue("Unexpected exception message: " + expected.toString()
					expected.getMessage().contains("time"));
			assertFalse("Unexpected exception message: " + expected.toString()
					expected.getMessage().contains("auth"));
		}
	}

