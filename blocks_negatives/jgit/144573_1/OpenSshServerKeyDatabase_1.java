			HostConfigEntry entry = session.getHostConfigEntry();
			String value = entry
					.getProperty(SshConstants.STRICT_HOST_KEY_CHECKING, "ask"); //$NON-NLS-1$
			switch (value.toLowerCase(Locale.ROOT)) {
			case SshConstants.YES:
			case SshConstants.ON:
