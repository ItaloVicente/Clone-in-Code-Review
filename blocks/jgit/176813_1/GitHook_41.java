	protected void handleError(String message
			final ProcessResult result)
			throws AbortedByHookException {
		throw new AbortedByHookException(message
				result.getExitCode());
	}

