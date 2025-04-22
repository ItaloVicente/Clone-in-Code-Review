	private String message;

	@Option(name = "--sign"
			"-s" }
	private boolean sign;

	@Option(name = "--no-sign"
			"--sign" })
	private boolean noSign;

	@Option(name = "--local-user"
			"-u" }
	private String gpgKeyId;
