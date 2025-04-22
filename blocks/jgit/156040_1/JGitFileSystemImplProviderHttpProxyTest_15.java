	@Test
	public void testHttpProxy() throws IOException {
		final String userName = "user";
		final String passw = "passwd";

		final JGitFileSystemProvider provider = new JGitFileSystemProvider(new HashMap<String
			{
				put("http.proxyUser"
				put("http.proxyPassword"
				put(GIT_DAEMON_ENABLED
				put(GIT_SSH_ENABLED
			}
		});

		final PasswordAuthentication passwdAuth = requestPasswordAuthentication("localhost"
				8080

		assertEquals(userName
		assertEquals(passw

		provider.dispose();
	}

	@Test
	public void testHttpsProxy() throws IOException {
		final String userName = "user";
		final String passw = "passwd";

		final JGitFileSystemProvider provider = new JGitFileSystemProvider(new HashMap<String
			{
				put("https.proxyUser"
				put("https.proxyPassword"
				put(GIT_DAEMON_ENABLED
				put(GIT_SSH_ENABLED
			}
		});

		final PasswordAuthentication passwdAuth = requestPasswordAuthentication("localhost"
				8080

		assertEquals(userName
		assertEquals(passw

		provider.dispose();
	}

	@Test
	public void testNoProxyInfo() throws IOException {
		final JGitFileSystemProvider provider = new JGitFileSystemProvider(new HashMap<String
			{
				put(GIT_DAEMON_ENABLED
				put(GIT_SSH_ENABLED
			}
		});

		{
			final PasswordAuthentication passwdAuth = requestPasswordAuthentication("localhost"
					InetAddress.getLocalHost()
					Authenticator.RequestorType.PROXY);

			assertNull(passwdAuth);
		}

		{
			final PasswordAuthentication passwdAuth = requestPasswordAuthentication("localhost"
					InetAddress.getLocalHost()
					Authenticator.RequestorType.PROXY);

			assertNull(passwdAuth);
		}

		provider.dispose();
	}
