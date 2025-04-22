		registry = RegistryFactory.getRegistry();
		if (configureElementProviders(DEPRECATED_ELEMENT_PROVIDER_EXTPOINT)) {
			System.err.println("Extension point "
					+ DEPRECATED_ELEMENT_PROVIDER_EXTPOINT
					+ " is deprecated; use " + ELEMENT_PROVIDER_EXTPOINT);
		}
		configureElementProviders(ELEMENT_PROVIDER_EXTPOINT);

