		try (Transport t = Transport.open(dst
			t.setCredentialsProvider(new UsernamePasswordCredentialsProvider(
					AppServer.username
			try {
				t.openFetch();
				fail("connection opened even info/refs needs auth basic and we provide wrong password");
			} catch (TransportException err) {
				String exp = dumbAuthBasicURI + ": "
						+ JGitText.get().notAuthorized;
				assertEquals(exp
			}
