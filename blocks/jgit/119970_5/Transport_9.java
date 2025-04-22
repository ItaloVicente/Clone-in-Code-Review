		RemoteConfig cfg;
		try {
			cfg = new RemoteConfig(local.getConfig()
		} catch (ConfigIllegalValueException e) {
			throw new TransportException(e.getMessage()
		}

