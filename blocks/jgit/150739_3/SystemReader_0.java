		private Path getXDGConfigHome(FS fs) {
			String configHomePath = SystemReader.getInstance()
					.getenv(Constants.XDG_CONFIG_HOME);
			if (StringUtils.isEmptyOrNull(configHomePath)) {
				configHomePath = new File(fs.userHome()
						.getAbsolutePath();
			}
			if (!StringUtils.isEmptyOrNull(configHomePath)) {
				Path xdgHomePath = null;
				try {
					xdgHomePath = Paths.get(configHomePath);
					Files.createDirectories(xdgHomePath);
					return xdgHomePath;
				} catch (IOException | InvalidPathException e) {
					LOG.error(JGitText.get().createXDGConfigHomeFailed
							xdgHomePath
				}
			}
			return null;
		}

		@Override
		public FileBasedConfig openJGitConfig(Config parent
			Path xdgPath = getXDGConfigHome(fs);
			if (xdgPath != null) {
				Path configPath = null;
				try {
					Files.createDirectories(configPath);
					configPath = configPath.resolve(Constants.CONFIG);
					return new FileBasedConfig(parent
				} catch (IOException e) {
					LOG.error(JGitText.get().createJGitConfigFailed
							e);
				}
			}
			return new FileBasedConfig(parent
					new File(fs.userHome()
		}

