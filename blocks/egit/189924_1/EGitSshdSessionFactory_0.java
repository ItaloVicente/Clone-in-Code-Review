
	private static class WrappedSshAgentConnectorFactory
			implements ConnectorFactory {

		private static final AtomicBoolean WARNED = new AtomicBoolean();

		private final ConnectorFactory delegate;

		WrappedSshAgentConnectorFactory(@NonNull ConnectorFactory realFactory) {
			delegate = realFactory;
		}

		@Override
		public Connector create(String identityAgent, File homeDir)
				throws IOException {
			String agentConnection = identityAgent;
			if (StringUtils.isEmptyOrNull(identityAgent)) {
				String preference = Platform.getPreferencesService().getString(
						Activator.PLUGIN_ID, GitCorePreferences.core_sshDefaultAgent,
						null, null);
				if (preference != null) {
					if (getSupportedConnectors().stream().anyMatch(d -> preference.equals(d.getIdentityAgent()))) {
						agentConnection = preference;
					} else if (!WARNED.getAndSet(true)) {
						Activator.logWarning(MessageFormat.format(
								CoreText.EGitSshdSessionFactory_sshUnknownAgentWarning,
								preference), null);
					}
				}
			}
			return delegate.create(agentConnection, homeDir);
		}

		@Override
		public boolean isSupported() {
			return delegate.isSupported();
		}

		@Override
		public String getName() {
			return delegate.getName();
		}

		@Override
		public Collection<ConnectorDescriptor> getSupportedConnectors() {
			return delegate.getSupportedConnectors();
		}

		@Override
		public ConnectorDescriptor getDefaultConnector() {
			return delegate.getDefaultConnector();
		}
	}

