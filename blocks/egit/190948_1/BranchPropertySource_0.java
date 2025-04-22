		if (value == null || value.isEmpty()) {
			if (ConfigConstants.CONFIG_KEY_PUSH_REMOTE.equals(actId)) {
				value = config.getString(ConfigConstants.CONFIG_REMOTE_SECTION,
						null, ConfigConstants.CONFIG_KEY_PUSH_DEFAULT);
				String source = null;
				if (value == null) {
					value = config.getString(
							ConfigConstants.CONFIG_BRANCH_SECTION, myBranchName,
							ConfigConstants.CONFIG_KEY_REMOTE);
					if (value != null) {
						source = UIText.BranchPropertySource_RemoteDescriptor;
					}
				} else {
					source = ConfigConstants.CONFIG_REMOTE_SECTION + '.'
							+ ConfigConstants.CONFIG_KEY_PUSH_DEFAULT;
				}
				if (value != null) {
					return MessageFormat.format(
							UIText.BranchPropertySource_ValueNotSetDefault,
							source, value);
				}
			}
