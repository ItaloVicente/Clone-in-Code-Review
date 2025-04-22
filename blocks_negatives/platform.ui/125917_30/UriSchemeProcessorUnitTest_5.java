	public void throwExceptionOnWrongRegisteredType() throws Exception {
		Object handlerWithWrongType = new Object();
		ConfigElementMock element = new ConfigElementMock(ABC_SCHEME, handlerWithWrongType);
		registerExtensions(schemeProcessor, element);

		schemeProcessor.handleUri(ABC_SCHEME, ABC_URI);
	}

	private void registerExtensions(IUriSchemeProcessor instance, IConfigurationElement... element) throws Exception {
		Field configurationElementsFields = UriSchemeProcessor.class.getDeclaredField("configurationElements");
		configurationElementsFields.setAccessible(true);
		configurationElementsFields.set(instance, element);
