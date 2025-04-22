	static boolean containsSigningKey(String userId
		if (StringUtils.isEmptyOrNull(userId)
				|| StringUtils.isEmptyOrNull(signingKeySpec)) {
			return false;
		}
		String toMatch = signingKeySpec;
		}
		int command = toMatch.charAt(0);
		switch (command) {
		case '=':
		case '<':
		case '@':
		case '*':
			toMatch = toMatch.substring(1);
			if (toMatch.isEmpty()) {
				return false;
			}
			break;
		default:
			break;
		}
		switch (command) {
		case '=':
			return userId.equals(toMatch);
		case '<': {
			int begin = userId.indexOf('<');
			int end = userId.indexOf('>'
			int stop = toMatch.indexOf('>');
			return begin >= 0 && end > begin + 1 && stop > 0
					&& userId.substring(begin + 1
							.equals(toMatch.substring(0
		}
		case '@': {
			int begin = userId.indexOf('<');
			int end = userId.indexOf('>'
			return begin >= 0 && end > begin + 1
					&& userId.substring(begin + 1
		}
		default:
			if (toMatch.trim().isEmpty()) {
				return false;
			}
			return userId.toLowerCase(Locale.ROOT)
					.contains(toMatch.toLowerCase(Locale.ROOT));
		}
	}

	private String toFingerprint(String keyId) {
			return keyId.substring(2);
		}
		return keyId;
