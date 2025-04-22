	private static Pattern remote(String hosts) {
		return Pattern
				.compile("(?:(?:https?|ssh)://)?(?:[^@:/]+(?::[^@:/]*)?@)?(?:" //$NON-NLS-1$
						+ hosts + ")[:/][^/]+/.*\\.git"); //$NON-NLS-1$
	}

