	private int getNumberOfPasswordPrompts(HostConfigEntry hostConfig) {
		String prompts = hostConfig
				.getProperty(SshConstants.NUMBER_OF_PASSWORD_PROMPTS);
		if (prompts != null) {
			prompts = prompts.trim();
			int value = positive(prompts);
			if (value > 0) {
				return value;
			}
			log.warn(format(SshdText.get().configInvalidPositive
					SshConstants.NUMBER_OF_PASSWORD_PROMPTS
		}
		return 3;
	}

