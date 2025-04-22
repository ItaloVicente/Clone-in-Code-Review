		String trustStr = rc.getString(ConfigConstants.CONFIG_DIFFTOOL_SECTION
				null
		if (trustStr != null) {
			trustExitCode = BooleanOption
					.defined(Boolean.parseBoolean(trustStr));
		} else {
			trustExitCode = BooleanOption.notDefined(false);
		}
