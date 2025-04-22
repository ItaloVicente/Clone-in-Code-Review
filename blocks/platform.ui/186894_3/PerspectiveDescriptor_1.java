
	@Override
	public String getDefaultShowIn() {
		return defaultShowIn == null && configElement != null
				? configElement.getAttribute(IWorkbenchRegistryConstants.ATT_DEFAULT_SHOW_IN)
				: defaultShowIn;
	}
