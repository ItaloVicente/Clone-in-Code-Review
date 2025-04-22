	private static String simplify(String refName) {
		if (refName.startsWith(Constants.R_HEADS))
			return refName.substring(Constants.R_HEADS.length());
		if (refName.startsWith(Constants.R_TAGS))
			return refName.substring(Constants.R_TAGS.length());
		if (refName.startsWith(Constants.R_REFS))
			return refName.substring(Constants.R_REFS.length());
		return refName;
	}

