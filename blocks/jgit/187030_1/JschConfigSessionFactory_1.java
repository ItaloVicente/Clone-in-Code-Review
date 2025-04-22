	private void copyGlobalConfigIfNotSet(String from
		String toValue = JSch.getConfig(to);
		if (StringUtils.isEmptyOrNull(toValue)) {
			String fromValue = JSch.getConfig(from);
			if (!StringUtils.isEmptyOrNull(fromValue)) {
				JSch.setConfig(to
			}
		}
	}

