		private String getXDGConfigHome(FS fs) {
			String configHomePath = System.getenv(Constants.XDG_CONFIG_HOME);
			return configHomePath == null ? fs.userHome().getAbsolutePath()
					: configHomePath;
		}

		@Override
		public FileBasedConfig openJGitConfig(Config parent
			Path jgitConfigPath = Paths.get(getXDGConfigHome(fs)
					".config"
			return new FileBasedConfig(parent
		}

