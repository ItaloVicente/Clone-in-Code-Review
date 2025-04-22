	private final class MockConfig extends FileBasedConfig {
		private MockConfig(File cfgLocation
			super(cfgLocation
		}

		@Override
		public void load() throws IOException
		}

		@Override
		public boolean isOutdated() {
			return false;
		}
	}

