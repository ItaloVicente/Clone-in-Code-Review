
			@Override
			protected ConnectorFactory getConnectorFactory() {
				if (connectorFactorySet) {
					return connectorFactory;
				}
				return super.getConnectorFactory();
			}
