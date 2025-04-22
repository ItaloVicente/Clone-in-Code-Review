		try {
			return Pattern.compile(sb.toString());
		} catch (PatternSyntaxException e) {
			InvalidPatternException patternException = new InvalidPatternException(
							+ "'."
					pattern);
			patternException.initCause(e);
			throw patternException;
		}
