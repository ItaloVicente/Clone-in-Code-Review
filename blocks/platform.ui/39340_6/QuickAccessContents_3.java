	private IEclipseContext context;

	private IRegistryChangeListener registryChangeListener = new IRegistryChangeListener() {
		@Override
		public void registryChanged(IRegistryChangeEvent event) {
			computeProviders();
			resetProviders();
		}
	};
