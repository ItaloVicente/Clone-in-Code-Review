					ConfigConstants.CONFIG_KEY_PUSH_REMOTE);
			if (remoteName == null) {
				if (defaultPushRemote != null) {
					remoteName = defaultPushRemote;
				} else {
					remoteName = config.getString(
							ConfigConstants.CONFIG_BRANCH_SECTION, branch,
							ConfigConstants.CONFIG_KEY_REMOTE);
				}
			}
		}
		if (defaultPushRemote == null) {
			defaultPushRemote = Constants.DEFAULT_REMOTE_NAME;
		}
