	@Nullable
	public List<String> getPushOptions() {
		if (!isAllowPushOptions()) {
			throw new IllegalStateException();
		}
		checkRequestWasRead();
		if (!usePushOptions) {
			return null;
		}
		return Collections.unmodifiableList(pushOptions);
	}

