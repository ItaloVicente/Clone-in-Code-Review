				FastIgnoreRule rule = new FastIgnoreRule();
				try {
					rule.parse(txt);
				} catch (InvalidPatternException e) {
					if (sourceName != null) {
						LOG.error(MessageFormat.format(
								JGitText.get().badIgnorePatternFull
								Integer.toString(lineNumber)
								e.getLocalizedMessage())
					} else {
						LOG.error(MessageFormat.format(
								JGitText.get().badIgnorePattern
								e.getPattern())
					}
				}
