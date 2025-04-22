		public ReftableConfig getReftableConfig() {
			ReftableConfig cfg = new ReftableConfig();
			cfg.setAlignBlocks(false);
			cfg.setIndexObjects(false);
			cfg.fromConfig(getRepository().getConfig());
			return cfg;
