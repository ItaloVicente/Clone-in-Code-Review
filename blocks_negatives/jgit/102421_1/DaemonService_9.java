		configKey = new SectionParser<ServiceConfig>() {
			@Override
			public ServiceConfig parse(final Config cfg) {
				return new ServiceConfig(DaemonService.this, cfg, cfgName);
			}
		};
