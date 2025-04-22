	public void testListRemote_Dumb_Auth() throws Exception {
		Repository dst = createBareRepository();
		Transport t = Transport.open(dst
		t.setCredentialsProvider(new UsernamePasswordCredentialsProvider(
				AppServer.username
		try {
			t.openFetch();
		} finally {
			t.close();
		}
		t = Transport.open(dst
		t.setCredentialsProvider(new UsernamePasswordCredentialsProvider(
				AppServer.username
		try {
			t.openFetch();
			fail("connection opened even info/refs needs auth basic and we provide wrong password");
		} catch (TransportException err) {
			String exp = dumbAuthBasicURI + ": "
					+ JGitText.get().notAuthorized;
			assertEquals(exp
		} finally {
			t.close();
		}
	}

