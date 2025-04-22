	@Test
	public void testNoProxy() {
		JGitFileSystemProviderConfiguration config = new JGitFileSystemProviderConfiguration() {

		};
		config.load(new ConfigProperties(Collections.emptyMap()));

		final JGitSSHConfigSessionFactory instance = new JGitSSHConfigSessionFactory(config) {
			@Override
			ProxyHTTP buildProxy(final JGitFileSystemProviderConfiguration config) {
				fail("no proxy should be set");
				return null;
			}
		};
		instance.configure(mock(OpenSshConfig.Host.class)
	}

	@Test
	public void testHttpProxy() {
		JGitFileSystemProviderConfiguration config = new JGitFileSystemProviderConfiguration() {

		};
		config.load(new ConfigProperties(new HashMap<String
			{
				put(SSH_OVER_HTTP
				put("http.proxyHost"
				put("http.proxyPort"
			}
		}));

		final JGitSSHConfigSessionFactory instance = new JGitSSHConfigSessionFactory(config) {
			@Override
			ProxyHTTP buildProxy(final JGitFileSystemProviderConfiguration config) {
				ProxyHTTP proxy = super.buildProxy(config);
				assertThat(proxy).hasFieldOrPropertyWithValue("proxy_host"
				assertThat(proxy).hasFieldOrPropertyWithValue("proxy_port"
				assertThat(proxy).hasFieldOrPropertyWithValue("user"
				assertThat(proxy).hasFieldOrPropertyWithValue("passwd"
				return proxy;
			}
		};
		instance.configure(mock(OpenSshConfig.Host.class)
	}

	@Test
	public void testHttpProxyWithAuthentication() {
		JGitFileSystemProviderConfiguration config = new JGitFileSystemProviderConfiguration() {

		};
		config.load(new ConfigProperties(new HashMap<String
			{
				put(SSH_OVER_HTTP
				put("http.proxyHost"
				put("http.proxyPort"
				put("http.proxyUser"
				put("http.proxyPassword"
			}
		}));

		final JGitSSHConfigSessionFactory instance = new JGitSSHConfigSessionFactory(config) {
			@Override
			ProxyHTTP buildProxy(final JGitFileSystemProviderConfiguration config) {
				ProxyHTTP proxy = super.buildProxy(config);
				assertThat(proxy).hasFieldOrPropertyWithValue("proxy_host"
				assertThat(proxy).hasFieldOrPropertyWithValue("proxy_port"
				assertThat(proxy).hasFieldOrPropertyWithValue("user"
				assertThat(proxy).hasFieldOrPropertyWithValue("passwd"
				return proxy;
			}
		};
		instance.configure(mock(OpenSshConfig.Host.class)
	}

	@Test
	public void testHttpsProxy() {
		JGitFileSystemProviderConfiguration config = new JGitFileSystemProviderConfiguration() {

		};
		config.load(new ConfigProperties(new HashMap<String
			{
				put(SSH_OVER_HTTPS
				put("https.proxyHost"
				put("https.proxyPort"
			}
		}));

		final JGitSSHConfigSessionFactory instance = new JGitSSHConfigSessionFactory(config) {
			@Override
			ProxyHTTP buildProxy(final JGitFileSystemProviderConfiguration config) {
				ProxyHTTP proxy = super.buildProxy(config);
				assertThat(proxy).hasFieldOrPropertyWithValue("proxy_host"
				assertThat(proxy).hasFieldOrPropertyWithValue("proxy_port"
				assertThat(proxy).hasFieldOrPropertyWithValue("user"
				assertThat(proxy).hasFieldOrPropertyWithValue("passwd"
				return proxy;
			}
		};
		instance.configure(mock(OpenSshConfig.Host.class)
	}

	@Test
	public void testHttpsProxyWithAuthentication() {
		JGitFileSystemProviderConfiguration config = new JGitFileSystemProviderConfiguration() {

		};
		config.load(new ConfigProperties(new HashMap<String
			{
				put(SSH_OVER_HTTPS
				put("https.proxyHost"
				put("https.proxyPort"
				put("https.proxyUser"
				put("https.proxyPassword"
			}
		}));

		final JGitSSHConfigSessionFactory instance = new JGitSSHConfigSessionFactory(config) {
			@Override
			ProxyHTTP buildProxy(final JGitFileSystemProviderConfiguration config) {
				ProxyHTTP proxy = super.buildProxy(config);
				assertThat(proxy).hasFieldOrPropertyWithValue("proxy_host"
				assertThat(proxy).hasFieldOrPropertyWithValue("proxy_port"
				assertThat(proxy).hasFieldOrPropertyWithValue("user"
				assertThat(proxy).hasFieldOrPropertyWithValue("passwd"
				return proxy;
			}
		};
		instance.configure(mock(OpenSshConfig.Host.class)
	}
