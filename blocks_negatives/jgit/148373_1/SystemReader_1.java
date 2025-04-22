			FileBasedConfig c = userConfig.get();
			if (c == null) {
				userConfig.compareAndSet(null, new FileBasedConfig(parent,
						new File(fs.userHome(), ".gitconfig"), fs)); //$NON-NLS-1$
				c = userConfig.get();
			}
