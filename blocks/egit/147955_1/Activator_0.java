
		@Override
		public StoredConfig getUserConfig()
				throws IOException, ConfigInvalidException {
			return delegate.getUserConfig();
		}

		@Override
		public StoredConfig getSystemConfig()
				throws IOException, ConfigInvalidException {
			return delegate.getSystemConfig();
		}
