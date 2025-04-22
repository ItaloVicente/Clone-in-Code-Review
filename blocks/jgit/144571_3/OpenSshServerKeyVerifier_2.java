			HostConfigEntry entry = session.getHostConfigEntry();
			String value = entry
					.getProperty(SshConstants.STRICT_HOST_KEY_CHECKING
			switch (value.toLowerCase(Locale.ROOT)) {
			case SshConstants.YES:
			case SshConstants.ON:
				return Check.DENY;
			case SshConstants.NO:
			case SshConstants.OFF:
				return Check.ALLOW;
				return changed ? Check.DENY : Check.ALLOW;
			default:
				break;
