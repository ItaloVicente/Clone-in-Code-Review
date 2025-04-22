
	public String getMessages() {
		return messageBuffer != null ? messageBuffer.toString() : "";
	}

	void addMessages(final String msg) {
		if (msg != null && msg.length() > 0) {
			if (messageBuffer == null)
				messageBuffer = new StringBuilder();
			messageBuffer.append(msg);
			if (!msg.endsWith("\n"))
				messageBuffer.append('\n');
		}
	}
