	private static final IMarkerResolutionGenerator GENERATOR_ERROR = marker -> null;

	private static final IMarkerResolutionGenerator GENERATOR_NOT_ACTIVE = marker -> new IMarkerResolution[0];

	private static final IMarkerHelpContextProvider DUMMY_HELP_PROVIDER = new IMarkerHelpContextProvider() {

		@Override
		public boolean hasHelpContextForMarker(IMarker marker) {
			return false;
		}

		@Override
		public String getHelpContextForMarker(IMarker marker) {
			return null;
		}
	};

	private Map<IConfigurationElement, IMarkerResolutionGenerator> generatorMap;

	private Map<IConfigurationElement, IMarkerHelpContextProvider> helpProviderMap;

