				boolean isPrefixPattern = matchRule == SearchPattern.RULE_PREFIX_MATCH
						|| (matchRule == SearchPattern.RULE_PATTERN_MATCH && filenamePattern.endsWith("*")); //$NON-NLS-1$
				if (!isPrefixPattern)
					filenamePattern += '<';
				else if (filenamePattern.endsWith("*") && !filenamePattern.equals("**")) //$NON-NLS-1$ //$NON-NLS-2$
					filenamePattern = filenamePattern.substring(0, filenamePattern.length() - 1);
