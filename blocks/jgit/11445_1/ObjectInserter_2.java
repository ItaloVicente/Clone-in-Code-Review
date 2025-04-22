	public void flush() throws IOException {
		if (insReader != null)
			insReader.flush(0);
		else
			doFlush();
	}

	protected abstract void doFlush() throws IOException;
