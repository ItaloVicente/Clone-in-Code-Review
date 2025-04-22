	protected static boolean isAnyNull(CredentialItem... items) {
		for (CredentialItem i : items)
			if (i == null)
				return true;
		return false;
	}

