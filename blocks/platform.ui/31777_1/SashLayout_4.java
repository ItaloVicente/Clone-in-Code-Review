		Matcher matcher = patternAny.matcher(info);
		if (matcher.matches()) {
			if ("px".equals(matcher.group(2))) { //$NON-NLS-1$
				return 0;
			}
			try {
				int value = Integer.parseInt(matcher.group(1));
				return value;
			} catch (NumberFormatException e) {
				return 0;
			}
		} else {
			return 0;
		}
	}
