		boolean useBuiltin = config.getBoolean(Constants.ATTR_FILTER
				filterDriverName
		if (useBuiltin) {
			filterCommand = Constants.BUILTIN_FILTER_PREFIX + filterDriverName
					+ "/" + filterCommandType;
		} else {
			filterCommand = config.getString(Constants.ATTR_FILTER
