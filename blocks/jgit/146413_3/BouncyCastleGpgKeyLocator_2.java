		}
		if (exists(USER_PGP_LEGACY_SECRING_FILE)) {
			key = loadKeyFromSecring(USER_PGP_LEGACY_SECRING_FILE);
			if (key != null) {
				return key;
			}
