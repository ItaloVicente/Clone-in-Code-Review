		return exec(command
	}

	@Override
	public Process exec(String command
			int timeout) throws IOException {
		return new JschProcess(command
