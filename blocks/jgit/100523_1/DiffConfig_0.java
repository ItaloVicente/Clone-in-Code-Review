		external = readExternal(rc);
	}

	private String readExternal(Config rc) {
		if (!StringUtils.isEmptyOrNull(value)) {
			return value;
		}
		return rc.getString(ConfigConstants.CONFIG_DIFF_SECTION
