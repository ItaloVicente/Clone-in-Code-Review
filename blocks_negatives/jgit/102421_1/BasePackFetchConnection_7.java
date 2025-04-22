		static final SectionParser<FetchConfig> KEY = new SectionParser<FetchConfig>() {
			@Override
			public FetchConfig parse(final Config cfg) {
				return new FetchConfig(cfg);
			}
		};

