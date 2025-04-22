		return commitMessage;
	}

	public boolean isWithChangeId() {
		return useChangeId;
	}

	private boolean hasChangeIdFooter(String message) {
		int footer = CommonUtils.getFooterOffset(message);
		return footer > 0 && CHANGE_ID.matcher(message).find(footer);
