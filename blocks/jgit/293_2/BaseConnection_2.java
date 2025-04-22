
	protected Writer getMessageWriter() {
		if (messageWriter == null)
			setMessageWriter(new StringWriter());
		return messageWriter;
	}

	protected void setMessageWriter(Writer writer) {
		if (messageWriter != null)
			throw new IllegalStateException("Writer already initialized");
		messageWriter = writer;
	}
