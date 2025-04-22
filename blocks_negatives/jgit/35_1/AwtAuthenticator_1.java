	protected PasswordAuthentication getPasswordAuthentication() {
		for (final CachedAuthentication ca : cached) {
			if (ca.host.equals(getRequestingHost())
					&& ca.port == getRequestingPort())
				return ca.toPasswordAuthentication();
		}

