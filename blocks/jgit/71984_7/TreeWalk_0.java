		boolean useBuiltin = config.getBoolean(Constants.ATTR_FILTER
				filterDriverName
		if (useBuiltin) {
			String builtinFilterCommand = Constants.BUILTIN_FILTER_PREFIX
					+ filterDriverName
					+ "/" + filterCommandType;
			if (registeredCommands != null
					&& registeredCommands.contains(builtinFilterCommand)) {
				filterCommand = builtinFilterCommand;
			}
		}
		if (filterCommand != null) {
