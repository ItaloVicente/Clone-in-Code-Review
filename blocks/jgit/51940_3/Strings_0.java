		try {
			return Pattern.compile(sb.toString());
		} catch (PatternSyntaxException e) {
			InvalidPatternException patternException = new InvalidPatternException(
					MessageFormat.format(JGitText.get().invalidIgnoreRule
							pattern)
					pattern);
			patternException.initCause(e);
			throw patternException;
		}
