			if (includeLeadingWildcard) {
				if (stringPattern.indexOf("*") != 0 && stringPattern.indexOf("?") != 0 //$NON-NLS-1$ //$NON-NLS-2$
						&& stringPattern.indexOf(".") != 0) { //$NON-NLS-1$
					stringPattern = "*" + stringPattern; //$NON-NLS-1$
				}
				patternMatcher.setPattern(stringPattern);
			}
