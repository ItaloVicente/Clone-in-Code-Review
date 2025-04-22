		OpenSshConfigFile systemConfigFile;
		if (readSystemConfig)
			systemConfigFile = OpenSshConfigFile.getSystemConfigFile(fs);
		else
			systemConfigFile = OpenSshConfigFile.IGNORED;

		return new OpenSshConfig(userConfigFile
