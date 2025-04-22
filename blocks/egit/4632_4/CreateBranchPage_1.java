
	public static String getProposedTargetName(String sourceName) {
		if (sourceName == null)
			return null;

		if (sourceName.startsWith(Constants.R_REMOTES)) {
			String target = sourceName.substring(Constants.R_REMOTES.length());
			int postSlash = target.indexOf('/') + 1;
			if (postSlash > 0 && postSlash < target.length())
				return target.substring(postSlash);
			else
				return target;
		}

		if (sourceName.startsWith(Constants.R_TAGS))
			return sourceName.substring(Constants.R_TAGS.length());

		return null;
	}

