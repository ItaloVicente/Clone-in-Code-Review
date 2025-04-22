	private String buildReflogMessage(String commitMessage) {
		String firstLine = commitMessage;
		if (newlineIndex > 0) {
			firstLine = commitMessage.substring(0, newlineIndex);
		}
		String message = commitStr + firstLine;
		return message;
	}
