				boolean isPrefixPattern = getMatchRule() == SearchPattern.RULE_PREFIX_MATCH
						|| (getMatchRule() == SearchPattern.RULE_PATTERN_MATCH && filenamePattern.endsWith("*")); //$NON-NLS-1$
				if (!isPrefixPattern)
					patternMatcher.setPattern(filenamePattern + '<');
				else if (filenamePattern.endsWith("*") && !filenamePattern.equals("**")) //$NON-NLS-1$ //$NON-NLS-2$
					patternMatcher.setPattern(filenamePattern.substring(0, filenamePattern.length() - 1));
				else
					patternMatcher.setPattern(filenamePattern);
				filenamePattern = patternMatcher.getPattern();
