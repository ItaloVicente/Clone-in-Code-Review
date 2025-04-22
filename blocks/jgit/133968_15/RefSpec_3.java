	static boolean destinationIntersects(String thisPattern
			String thatPattern) {
		validatePartialWildcard(thisPattern);
		validatePartialWildcard(thatPattern);

		int pathSize = Math.min(thisPath.size()
		int commonPrefix;
		for (commonPrefix = 0; commonPrefix < pathSize; commonPrefix++) {
			String thisPiece = thisPath.get(commonPrefix);
			if (thisPiece.equals(WILDCARD)) {
				break;
			}
			if (!thisPiece.equals(thatPath.get(commonPrefix))) {
				break;
			}
		}
		int commonSuffix;
		for (commonSuffix = 0; commonSuffix < pathSize; commonSuffix++) {
			String thisPiece = thisPath.get(thisPath.size() - commonSuffix - 1);
			String thatPiece = thatPath.get(thatPath.size() - commonSuffix - 1);
			if (thisPiece.equals(WILDCARD)) {
				break;
			}
			if (!thisPiece.equals(thatPiece)) {
				break;
			}
		}
		if (commonPrefix > thisPath.size() - commonSuffix - 1
				|| commonPrefix > thatPath.size() - commonSuffix - 1) {
			return thisPath.equals(thatPath);
		}
		thisPath = thisPath.subList(commonPrefix
				thisPath.size() - commonSuffix);
		thatPath = thatPath.subList(commonPrefix
				thatPath.size() - commonSuffix);
		if (!thisPath.get(0).equals(WILDCARD)
				&& !thatPath.get(0).equals(WILDCARD)) {
			return false;
		}
		if (!thisPath.get(thisPath.size() - 1).equals(WILDCARD)
				&& !thatPath.get(thatPath.size() - 1).equals(WILDCARD)) {
			return false;
		}
		return true;

	}

	private static void validatePartialWildcard(String refPattern) {
		int wildcardIndex = refPattern.indexOf('*');
		if (wildcardIndex == -1) {
			return;
		}
		boolean prefixIsValid = wildcardIndex == 0
				|| refPattern.charAt(wildcardIndex - 1) == '/';
		boolean suffixIsValid = wildcardIndex == refPattern.length() - 1
				|| refPattern.charAt(wildcardIndex + 1) == '/';
		if (!prefixIsValid || !suffixIsValid) {
			throw new IllegalArgumentException(MessageFormat
					.format(JGitText.get().invalidRefName
		}
	}

