	private FileBasedConfig noopConfig(){
		return new FileBasedConfig(null
			public void load() {
			}

			public boolean isOutdated() {
				return false;
			}
		};
