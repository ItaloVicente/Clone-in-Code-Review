	@Deprecated
	public final void create(boolean bare) throws IOException {
		if (bare != isBare()) {
			throw new IllegalArgumentException(
					MessageFormat.format(JGitText.get().expectedGot
							Boolean.valueOf(isBare())
		}
		create();
	}
