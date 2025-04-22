	private static boolean destinationContains(String thisDstPattern
			String thatDstPattern) {
		checkValid(thatDstPattern);
		validatePartialWildcard(thisDstPattern);
		validatePartialWildcard(thatDstPattern);
		if (thisWildcard == -1) {
			return thisDst.equals(thatDst);
		}
		List<String> thisPrefix = thisDst.subList(0
		List<String> thisSuffix = thisDst.subList(thisWildcard + 1
				thisDst.size());
		return startsWith(thatDst
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

	private static boolean startsWith(List<String> list
			List<String> maybeStartsWith) {
		if (maybeStartsWith.size() > list.size()) {
			return false;
		}
		for (int i = 0; i < maybeStartsWith.size(); i++) {
			if (!maybeStartsWith.get(i).equals(list.get(i))) {
				return false;
			}
		}
		return true;
	}

	private static boolean endsWith(List<String> list
			List<String> maybeEndsWith) {
		if (maybeEndsWith.size() > list.size()) {
			return false;
		}
		int suffixSize = maybeEndsWith.size();
		int listSize = list.size();
		for (int i = 1; i <= maybeEndsWith.size(); i++) {
			if (!maybeEndsWith.get(suffixSize - i)
					.equals(list.get(listSize - i))) {
				return false;
			}
		}
		return true;
	}

