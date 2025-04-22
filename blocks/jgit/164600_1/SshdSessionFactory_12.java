	protected SshConfigStore createSshConfigStore(@NonNull File homeDir
			File configFile
		return configFile == null ? null
				: new OpenSshConfigFile(homeDir
	}

