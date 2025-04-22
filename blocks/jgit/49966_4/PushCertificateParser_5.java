		commands = new ArrayList<>();
		rawCommands = new StringBuilder();
	}

	@Deprecated
	public PushCertificateParser() {
		super();
		db = null;
		nonceSlopLimit = 0;
		nonceGenerator = null;
		commands = new ArrayList<>();
		rawCommands = new StringBuilder();
	}

	@Deprecated
	@Override
	public String getSignature() {
		return signature;
	}

	@Deprecated
	@Override
	public String getCommandList() {
		return rawCommands.toString();
	}

	@Deprecated
	@Override
	public String getPusher() {
		return pusher.toExternalString();
	}

	@Deprecated
	@Override
	public String getPushee() {
		return pushee;
	}

	@Deprecated
	@Override
	public NonceStatus getNonceStatus() {
		return nonceStatus;
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
