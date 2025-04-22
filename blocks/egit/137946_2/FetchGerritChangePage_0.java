		}, input -> {
			Change change = determineChangeFromString(input);
			int changeNumber = -1;
			try {
				if (change == null) {
					Matcher matcher = DIGITS.matcher(input);
					if (matcher.find()) {
						return Pattern.compile(GERRIT_CHANGE_REF_PREFIX
								+ "(../)?" + matcher.group() + WILDCARD); //$NON-NLS-1$
					} else if (input.startsWith(GERRIT_CHANGE_REF_PREFIX)
							|| GERRIT_CHANGE_REF_PREFIX.startsWith(input)) {
						return null; // Match all
					}
				} else {
					changeNumber = change.getChangeNumber().intValue();
				}
				if (changeNumber > 0) {
					return Pattern.compile(GERRIT_CHANGE_REF_PREFIX + "../" //$NON-NLS-1$
							+ changeNumber + WILDCARD);
				}
			} catch (NumberFormatException | PatternSyntaxException e) {
