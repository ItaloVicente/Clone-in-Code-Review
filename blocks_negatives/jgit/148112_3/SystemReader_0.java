			if (userConfig == null) {
				File home = fs.userHome();
				userConfig = new FileBasedConfig(parent,
						new File(home, ".gitconfig"), fs); //$NON-NLS-1$
			}
			return userConfig;
