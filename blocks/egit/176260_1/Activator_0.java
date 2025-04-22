	public void stop(final BundleContext context) throws Exception {
		secureStore = null;
		Config.setTypedConfigGetter(null);
		SystemReader.setInstance(null);
		super.stop(context);
		plugin = null;
