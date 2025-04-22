		commands = new ArrayList<>();
	}

	@Deprecated
	public PushCertificateParser() {
		super();
		db = null;
		nonceSlopLimit = 0;
		nonceGenerator = null;
		commands = null;
	}

	@Deprecated
	@Override
	public String getSignature() {
		return signature;
	}

	@Deprecated
	@Override
	public String getCommandList() {
		return getCommandBuilder(commands).toString();
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
		} catch (IllegalArgumentException e) {
			throw new IOException(e.getMessage()
		}
