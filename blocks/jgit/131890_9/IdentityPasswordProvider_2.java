	@Override
	public void setAttempts(int numberOfPasswordPrompts) {
		RepeatingFilePasswordProvider.super.setAttempts(
				numberOfPasswordPrompts);
		attempts = numberOfPasswordPrompts;
	}

	@Override
	public int getAttempts() {
		return Math.max(1
	}

	@Override
	public String getPassword(String resourceKey) throws IOException {
		char[] pass = getPassword(resourceKey
				current.computeIfAbsent(resourceKey
		if (pass == null) {
			return null;
		}
		try {
			return new String(pass);
		} finally {
			Arrays.fill(pass
		}
	}

	protected char[] getPassword(String resourceKey
			throws IOException {
		state.setPassword(null);
		state.incCount();
		String message = state.count == 1 ? SshdText.get().keyEncryptedMsg
				: SshdText.get().keyEncryptedRetry;
		char[] pass = getPassword(resourceKey
		state.setPassword(pass);
		return pass;
	}

