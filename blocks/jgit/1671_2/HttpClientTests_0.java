	public void testListRemote_Dumb_Auth() throws Exception {
		Repository dst = createBareRepository();
		Transport t = Transport.open(dst
		t.setCredentialsProvider(new TestCredentialsProvider(
				AppServer.username
		try {
			t.openFetch();
		} finally {
			t.close();
		}
		t = Transport.open(dst
		t.setCredentialsProvider(new TestCredentialsProvider(
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

	static class TestCredentialsProvider extends CredentialsProvider {
		static Set<CredentialType> supported;

		private String username;

		private String password;

		public TestCredentialsProvider(String username
			this.username = username;
			this.password = password;
		}
		public Set<CredentialType> getSupportedCredentialTypes() {
			if (supported == null) {
				supported = new HashSet<CredentialType>();
				supported.add(CredentialType.USERNAME);
				supported.add(CredentialType.PASSWORD);
			}
			return supported;
		}

		public String getCredentials(URIish uri
				throws UnsupportedCredentialType {
			switch (type) {
			case USERNAME:
				return username;
			case PASSWORD:
				return password;
			default:
				throw new UnsupportedCredentialType(uri
			}
		}
	}

