		public FileBasedConfig openSystemConfig(Config parent
			try {
				File prefix = fs.gitPrefix();
				if (prefix == null)
					return new FileBasedConfig(null
						public void load() throws IOException
								org.eclipse.jgit.errors.ConfigInvalidException {
						}
					};
				return new FileBasedConfig(parent
						"etc")
			} catch (IOException e) {
				return new FileBasedConfig(parent
			}
		}

		public FileBasedConfig openUserConfig(Config parent
