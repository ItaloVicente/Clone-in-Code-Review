		@Override
		public StoredConfig getSystemConfig()
				throws IOException
			return globalConfigCache.getSystemConfig();
		}

		@Override
		public StoredConfig getUserConfig()
				throws IOException
			return globalConfigCache.getUserConfig();
		}

