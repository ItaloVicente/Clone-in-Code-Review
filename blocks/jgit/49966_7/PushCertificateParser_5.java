		commands = new ArrayList<>();
		rawCommands = new StringBuilder();
	}

	public PushCertificate build() throws IOException {
		if (nonceGenerator == null) {
			return null;
		}
		try {
			return new PushCertificate(version
					nonceStatus
					rawCommands.toString()
		} catch (IllegalArgumentException e) {
			throw new IOException(e.getMessage()
		}
