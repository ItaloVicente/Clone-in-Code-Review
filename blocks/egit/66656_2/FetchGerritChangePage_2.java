	protected static String determineChangeFromString(String input) {
		if (input == null) {
			return null;
		}
		Pattern pattern = Pattern.compile(
				"(?:https?://\\S+?/|/)?([1-9][0-9]*)(?:/([1-9][0-9]*)(?:/([1-9][0-9]*)(?:..\\d+)?)?)?(?:/\\S*)?"); //$NON-NLS-1$
		Matcher matcher = pattern.matcher(input);
		if (matcher.matches()) {
			String first = matcher.group(1);
			String second = matcher.group(2);
			String third = matcher.group(3);
			if (second != null && !second.isEmpty()) {
				if (third != null && !third.isEmpty()) {
					return second;
				} else if (input.startsWith("http")) { //$NON-NLS-1$
					return first;
				} else {
					try {
						if (Integer.parseInt(first) > Integer
								.parseInt(second)) {
							return first;
						} else {
							return second;
						}
					} catch (NumberFormatException e) {
						return null;
					}
				}
			} else {
				return first;
			}
		}
		return null;
	}

