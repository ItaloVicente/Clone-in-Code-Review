				Path configPath = null;
				try {
					Files.createDirectories(configPath);
					configPath = configPath.resolve(Constants.CONFIG);
					return new FileBasedConfig(parent, configPath.toFile(), fs);
				} catch (IOException e) {
					LOG.error(JGitText.get().createJGitConfigFailed, configPath,
							e);
				}
