	@Override
	protected void initializeCSSElementProvider() {
		setElementProvider(new RegistryCSSElementProvider(
				RegistryFactory.getRegistry()));
	}

