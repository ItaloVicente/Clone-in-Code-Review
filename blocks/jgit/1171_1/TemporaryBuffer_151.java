	protected void doFlush() throws IOException {
		if (overflow == null)
			switchToOverflow();
		overflow.flush();
	}

