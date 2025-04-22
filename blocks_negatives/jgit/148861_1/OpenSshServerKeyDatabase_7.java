			if (session instanceof JGitClientSession) {
				HostConfigEntry entry = ((JGitClientSession) session)
						.getHostConfigEntry();
				String value = entry.getProperty(
						SshConstants.STRICT_HOST_KEY_CHECKING, "ask");
				switch (value.toLowerCase(Locale.ROOT)) {
				case SshConstants.YES:
				case SshConstants.ON:
					return Check.DENY;
				case SshConstants.NO:
				case SshConstants.OFF:
					return Check.ALLOW;
				case "accept-new":
					return changed ? Check.DENY : Check.ALLOW;
				default:
					break;
				}
			}
			if (getCredentialsProvider(session) == null) {
