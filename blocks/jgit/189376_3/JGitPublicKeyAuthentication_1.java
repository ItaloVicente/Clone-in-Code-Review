		if (agent != null) {
			parseAddKeys(hostConfig);
			if (addKeysToAgent) {
				skProvider = hostConfig.getProperty(SshConstants.SECURITY_KEY_PROVIDER);
			}
		}
