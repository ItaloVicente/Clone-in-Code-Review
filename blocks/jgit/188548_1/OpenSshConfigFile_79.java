			if (fillDefaults) {
				String s = options.get(SshConstants.USER);
				if (StringUtils.isEmptyOrNull(s)) {
					options.put(SshConstants.USER
				}
				if (positive(options.get(SshConstants.PORT)) <= 0) {
					options.put(SshConstants.PORT
				}
				if (positive(
						options.get(SshConstants.CONNECTION_ATTEMPTS)) <= 0) {
					options.put(SshConstants.CONNECTION_ATTEMPTS
				}
			}
