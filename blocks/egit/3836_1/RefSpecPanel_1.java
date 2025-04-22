	private String deleteTagPrefix(String newDst) {
		String result = newDst;
		int refsLength = Constants.R_REFS.length();
		String tagsString = Constants.R_TAGS.substring(refsLength);
		if (result.startsWith(tagsString))
			result = result.substring(tagsString.length());
		return result;
	}

	private String deleteHeadsPrefix(String newDst) {
		String result = newDst;
		int refsLength = Constants.R_REFS.length();
		String headsString = Constants.R_HEADS.substring(refsLength);
		if (result.startsWith(headsString))
			result = result.substring(headsString.length());
		return result;
	}

