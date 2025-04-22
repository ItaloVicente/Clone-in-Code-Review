
	@Override
	public Collection<ConnectorDescriptor> getSupportedConnectors() {
		return Collections.singleton(getDefaultConnector());
	}

	@Override
	public ConnectorDescriptor getDefaultConnector() {
		if (SystemReader.getInstance().isWindows()) {
			return PageantConnector.DESCRIPTOR;
		}
		return UnixDomainSocketConnector.DESCRIPTOR;
	}
