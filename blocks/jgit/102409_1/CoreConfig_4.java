		switch (logAllRefUpdates) {
			case FALSE:
				return false;
			case TRUE:
			case ALWAYS:
				return true;
			default:
				throw new IllegalStateException(
						MessageFormat.format(
								JGitText.get().enumValueNotSupported0
								logAllRefUpdates.name()));
		}
	}

	public LogAllRefUpdates getLogAllRefUpdates() {
