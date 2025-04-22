		if (RefSpec.isWildcard(s)) {
			return isValidRefExpression(s.substring(0, s.length() - 1) + 'X');
		} else
			return Repository.isValidRefName(s)
					|| Repository.isValidRefName(Constants.R_HEADS + s)
					|| Repository.isValidRefName(Constants.R_TAGS + s);
