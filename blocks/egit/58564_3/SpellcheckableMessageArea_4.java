	protected static String wrapCommitMessage(String text) {
		int footerStart = CommonUtils.getFooterOffset(text);
		if (footerStart < 0) {
			return hardWrap(text);
		} else {
			String footer = text.substring(footerStart);
			text = hardWrap(text.substring(0, footerStart));
			return text + footer;
		}
	}

