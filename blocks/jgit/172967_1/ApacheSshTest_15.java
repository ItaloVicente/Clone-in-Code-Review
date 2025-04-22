		try (Git git = Git.open(localClone)) {
			git.fetch().call();
			git.fetch().call();
		}
	}

	private SshServer createProxy(String user
			SshdSocketAddress[] report) throws Exception {
		SshServer proxy = SshServer.setUpDefaultServer();
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048);
		KeyPair proxyHostKey = generator.generateKeyPair();
		proxy.setKeyPairProvider(
				session -> Collections.singletonList(proxyHostKey));
		proxy.setUserAuthFactories(Collections.singletonList(
				ServerAuthenticationManager.DEFAULT_USER_AUTH_PUBLIC_KEY_FACTORY));
		PublicKey userProxyKey = AuthorizedKeyEntry
				.readAuthorizedKeys(userKey.toPath()).get(0)
				.resolvePublicKey(null
		proxy.setPublickeyAuthenticator(
				(userName
						&& KeyUtils.compareKeys(userProxyKey
		proxy.setForwardingFilter(new StaticDecisionForwardingFilter(true) {

			@Override
			protected boolean checkAcceptance(String request
					SshdSocketAddress target) {
				report[0] = target;
				return super.checkAcceptance(request
			}
		});
		proxy.start();
		try (BufferedWriter writer = Files.newBufferedWriter(
				knownHosts.toPath()
				StandardOpenOption.WRITE
			writer.append('\n');
			KnownHostHashValue.appendHostPattern(writer
					proxy.getPort());
			writer.append('
			KnownHostHashValue.appendHostPattern(writer
					proxy.getPort());
			writer.append(' ');
			PublicKeyEntry.appendPublicKeyEntry(writer
					proxyHostKey.getPublic());
			writer.append('\n');
		}
		return proxy;
	}

	@Test
	public void testJumpHost() throws Exception {
		SshdSocketAddress[] forwarded = { null };
		try (SshServer proxy = createProxy(TEST_USER + 'X'
				forwarded)) {
			try {
						"Host server"
						"HostName localhost"
						"Port " + testPort
						"User " + TEST_USER
						"IdentityFile " + privateKey1.getAbsolutePath()
						"ProxyJump " + TEST_USER + "X@proxy:" + proxy.getPort()
						""
						"Host proxy"
						"Hostname localhost"
						"IdentityFile " + privateKey2.getAbsolutePath());
				assertNotNull(forwarded[0]);
				assertEquals(testPort
			} finally {
				proxy.stop();
			}
		}
	}

	@Test
	public void testJumpHostWrongKeyAtProxy() throws Exception {
		SshdSocketAddress[] forwarded = { null };
		try (SshServer proxy = createProxy(TEST_USER + 'X'
				forwarded)) {
			try {
				TransportException e = assertThrows(TransportException.class
								defaultCloneDir
								"Host server"
								"HostName localhost"
								"Port " + testPort
								"User " + TEST_USER
								"IdentityFile " + privateKey1.getAbsolutePath()
								"ProxyJump " + TEST_USER + "X@proxy:"
										+ proxy.getPort()
								""
								"Host proxy"
								"Hostname localhost"
								"IdentityFile "
										+ privateKey1.getAbsolutePath()));
				String message = e.getMessage();
				assertTrue(message.contains("localhost:" + proxy.getPort()));
				assertTrue(message.contains("proxy:" + proxy.getPort()));
			} finally {
				proxy.stop();
			}
		}
	}

	@Test
	public void testJumpHostWrongKeyAtServer() throws Exception {
		SshdSocketAddress[] forwarded = { null };
		try (SshServer proxy = createProxy(TEST_USER + 'X'
				forwarded)) {
			try {
				TransportException e = assertThrows(TransportException.class
								defaultCloneDir
								"Host server"
								"HostName localhost"
								"Port " + testPort
								"User " + TEST_USER
								"IdentityFile " + privateKey2.getAbsolutePath()
								"ProxyJump " + TEST_USER + "X@proxy:"
										+ proxy.getPort()
								""
								"Host proxy"
								"Hostname localhost"
								"IdentityFile "
										+ privateKey2.getAbsolutePath()));
				String message = e.getMessage();
				assertTrue(message.contains("localhost:" + testPort));
			} finally {
				proxy.stop();
			}
		}
	}

	@Test
	public void testJumpHostNonSsh() throws Exception {
		SshdSocketAddress[] forwarded = { null };
		try (SshServer proxy = createProxy(TEST_USER + 'X'
				forwarded)) {
			try {
				TransportException e = assertThrows(TransportException.class
								defaultCloneDir
								"Host server"
								"HostName localhost"
								"Port " + testPort
								"User " + TEST_USER
								"IdentityFile " + privateKey1.getAbsolutePath()
										+ proxy.getPort()
								""
								"Host proxy"
								"Hostname localhost"
								"IdentityFile "
										+ privateKey2.getAbsolutePath()));
				Throwable t = e;
				while (t != null) {
					if (t instanceof URISyntaxException) {
						break;
					}
					t = t.getCause();
				}
				assertNotNull(t);
				assertTrue(t.getMessage().contains("Non-ssh"));
			} finally {
				proxy.stop();
			}
		}
	}

	@Test
	public void testJumpHostWithPath() throws Exception {
		SshdSocketAddress[] forwarded = { null };
		try (SshServer proxy = createProxy(TEST_USER + 'X'
				forwarded)) {
			try {
				TransportException e = assertThrows(TransportException.class
								defaultCloneDir
								"Host server"
								"HostName localhost"
								"Port " + testPort
								"User " + TEST_USER
								"IdentityFile " + privateKey1.getAbsolutePath()
										+ proxy.getPort() + "/wrongPath"
								""
								"Host proxy"
								"Hostname localhost"
								"IdentityFile "
										+ privateKey2.getAbsolutePath()));
				Throwable t = e;
				while (t != null) {
					if (t instanceof URISyntaxException) {
						break;
					}
					t = t.getCause();
				}
				assertNotNull(t);
				assertTrue(t.getMessage().contains("wrongPath"));
			} finally {
				proxy.stop();
			}
		}
	}

	@Test
	public void testJumpHostWithPathShort() throws Exception {
		SshdSocketAddress[] forwarded = { null };
		try (SshServer proxy = createProxy(TEST_USER + 'X'
				forwarded)) {
			try {
				TransportException e = assertThrows(TransportException.class
								defaultCloneDir
								"Host server"
								"HostName localhost"
								"Port " + testPort
								"User " + TEST_USER
								"IdentityFile " + privateKey1.getAbsolutePath()
								"ProxyJump " + TEST_USER + "X@proxy:wrongPath"
								""
								"Host proxy"
								"Hostname localhost"
								"Port " + proxy.getPort()
								"IdentityFile "
										+ privateKey2.getAbsolutePath()));
				Throwable t = e;
				while (t != null) {
					if (t instanceof URISyntaxException) {
						break;
					}
					t = t.getCause();
				}
				assertNotNull(t);
				assertTrue(t.getMessage().contains("wrongPath"));
			} finally {
				proxy.stop();
			}
		}
	}

	@Test
	public void testJumpHostChain() throws Exception {
		SshdSocketAddress[] forwarded1 = { null };
		SshdSocketAddress[] forwarded2 = { null };
		try (SshServer proxy1 = createProxy(TEST_USER + 'X'
				forwarded1);
				SshServer proxy2 = createProxy("foo"
			try {
						"Host server"
						"HostName localhost"
						"Port " + testPort
						"User " + TEST_USER
						"IdentityFile " + privateKey1.getAbsolutePath()
						"ProxyJump proxy2
								+ proxy1.getPort()
						""
						"Host proxy"
						"Hostname localhost"
						"IdentityFile " + privateKey2.getAbsolutePath()
						""
						"Host proxy2"
						"Hostname localhost"
						"User foo"
						"Port " + proxy2.getPort()
						"IdentityFile " + privateKey1.getAbsolutePath());
				assertNotNull(forwarded1[0]);
				assertEquals(proxy2.getPort()
				assertNotNull(forwarded2[0]);
				assertEquals(testPort
			} finally {
				proxy1.stop();
				proxy2.stop();
			}
		}
	}

	@Test
	public void testJumpHostCascade() throws Exception {
		SshdSocketAddress[] forwarded1 = { null };
		SshdSocketAddress[] forwarded2 = { null };
		try (SshServer proxy1 = createProxy(TEST_USER + 'X'
				forwarded1);
				SshServer proxy2 = createProxy("foo"
			try {
						"Host server"
						"HostName localhost"
						"Port " + testPort
						"User " + TEST_USER
						"IdentityFile " + privateKey1.getAbsolutePath()
						"ProxyJump " + TEST_USER + "X@proxy"
						""
						"Host proxy"
						"Hostname localhost"
						"Port " + proxy1.getPort()
						"IdentityFile " + privateKey2.getAbsolutePath()
						""
						"Host proxy2"
						"Hostname localhost"
						"User foo"
						"IdentityFile " + privateKey1.getAbsolutePath());
				assertNotNull(forwarded1[0]);
				assertEquals(testPort
				assertNotNull(forwarded2[0]);
				assertEquals(proxy1.getPort()
			} finally {
				proxy1.stop();
				proxy2.stop();
			}
		}
	}

	@Test
	public void testJumpHostRecursion() throws Exception {
		SshdSocketAddress[] forwarded1 = { null };
		SshdSocketAddress[] forwarded2 = { null };
		try (SshServer proxy1 = createProxy(TEST_USER + 'X'
				forwarded1);
				SshServer proxy2 = createProxy("foo"
			try {
				TransportException e = assertThrows(TransportException.class
						() -> cloneWith(
						"Host server"
						"HostName localhost"
						"Port " + testPort
						"User " + TEST_USER
						"IdentityFile " + privateKey1.getAbsolutePath()
						"ProxyJump " + TEST_USER + "X@proxy"
						""
						"Host proxy"
						"Hostname localhost"
						"Port " + proxy1.getPort()
						"IdentityFile " + privateKey2.getAbsolutePath()
						""
						"Host proxy2"
						"Hostname localhost"
						"User foo"
						"ProxyJump " + TEST_USER + "X@proxy"
						"IdentityFile " + privateKey1.getAbsolutePath()));
				assertTrue(e.getMessage().contains("proxy"));
			} finally {
				proxy1.stop();
				proxy2.stop();
			}
		}
