	public static String shortenRefName(String noteRefName) {
		if (noteRefName.startsWith(Constants.R_NOTES))
			return noteRefName.substring(Constants.R_NOTES.length());
		return noteRefName;
	}

