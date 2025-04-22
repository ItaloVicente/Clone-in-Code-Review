		String text = getCommitMessage();
		int footer = CommonUtils.getFooterOffset(text);
		if (footer >= 0) {
			text = text.substring(0, footer);
		}
		if (!ANY_NON_WHITESPACE.matcher(text).find()) {
