		static final SectionParser<ReceiveConfig> KEY = new SectionParser<ReceiveConfig>() {
			@Override
			public ReceiveConfig parse(final Config cfg) {
				return new ReceiveConfig(cfg);
			}
		};

