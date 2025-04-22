	static String getKeyId(ClientSession session
		String fingerprint = KeyUtils.getFingerPrint(identity.getPublic());
		Map<String
				.getAttribute(KEY_PATHS_BY_FINGERPRINT);
		if (registered != null) {
			Path path = registered.get(fingerprint);
			if (path != null) {
				Path home = session
						.resolveAttribute(JGitSshClient.HOME_DIRECTORY);
				if (home != null && path.startsWith(home)) {
					try {
						path = home.relativize(path);
						String pathString = path.toString();
						if (!pathString.isEmpty()) {
						}
					} catch (IllegalArgumentException e) {
					}
				}
				return path.toString();
			}
		}
		return fingerprint;
	}

