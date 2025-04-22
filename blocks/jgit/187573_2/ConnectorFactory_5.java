	interface ConnectorDescriptor {

		@NonNull
		String getIdentityAgent();

		@NonNull
		String getDisplayName();
	}

	@NonNull
	Collection<ConnectorDescriptor> getSupportedConnectors();

	ConnectorDescriptor getDefaultConnector();
