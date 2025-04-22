	protected void processError(final ByteArrayOutputStream errorByteArray
			final ProcessResult result) throws AbortedByHookException {
		throw new AbortedByHookException(
				new String(errorByteArray.toByteArray()
				result.getExitCode());
	}

