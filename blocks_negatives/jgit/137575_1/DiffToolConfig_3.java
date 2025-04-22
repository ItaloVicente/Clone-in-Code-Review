			boolean trust = trustExitCode;
			String trustStr = rc.getString(
					ConfigConstants.CONFIG_DIFFTOOL_SECTION,
					name, ConfigConstants.CONFIG_KEY_TRUST_EXIT_CODE);
			if (trustStr != null) {
				trust = Boolean.getBoolean(trustStr);
