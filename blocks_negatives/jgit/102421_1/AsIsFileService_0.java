	private static final SectionParser<ServiceConfig> CONFIG = new SectionParser<ServiceConfig>() {
		@Override
		public ServiceConfig parse(final Config cfg) {
			return new ServiceConfig(cfg);
		}
	};

