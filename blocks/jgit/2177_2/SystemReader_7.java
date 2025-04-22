		public FileBasedConfig openSystemConfig(Config parent
			File prefix = fs.gitPrefix();
			if (prefix == null) {
				return new FileBasedConfig(null
					public void load() {
					}

					public boolean isOutdated() {
						return false;
					}
				};
			}
			File etc = fs.resolve(prefix
			File config = fs.resolve(etc
			return new FileBasedConfig(parent
		}

		public FileBasedConfig openUserConfig(Config parent
