
		if (useRegex) {
			if (containsModifier.equals(MarkerSupportConstants.CONTAINS_KEY))
				return Pattern.matches(containsText, value);
			return !Pattern.matches(containsText, value);
		}
