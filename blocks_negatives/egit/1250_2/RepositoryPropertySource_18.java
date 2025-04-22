		String actId = (String) id;
		try {
			if (actId.startsWith(USER_ID_PREFIX)) {
				setConfigValue(userHomeConfig, actId.substring(4),
						(String) value);
			}
			if (actId.startsWith(REPO_ID_PREFIX)) {
				setConfigValue(repositoryConfig, actId.substring(4),
						(String) value);
			}
		} catch (IOException e) {
			showExceptionMessage(e);
