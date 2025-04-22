		String actId = ((String) id);
		Object value = null;
		if (actId.startsWith(USER_ID_PREFIX)) {
			value = getValueFromConfig(userHomeConfig, actId.substring(4));
		} else if (actId.startsWith(REPO_ID_PREFIX)) {
			value = getValueFromConfig(repositoryConfig, actId.substring(4));
		} else if (actId.startsWith(EFFECTIVE_ID_PREFIX)) {
			value = getValueFromConfig(effectiveConfig, actId.substring(4));
		}
		return value != null;
