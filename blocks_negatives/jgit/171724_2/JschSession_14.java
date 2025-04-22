		return exec(command, Collections.emptyMap(), timeout);
	}

	/** {@inheritDoc} */
	@Override
	public Process exec(String command, Map<String, String> environment,
			int timeout) throws IOException {
		return new JschProcess(command, environment, timeout);
