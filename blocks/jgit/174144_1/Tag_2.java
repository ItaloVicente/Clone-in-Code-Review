	private String message;

	@Option(name = "--sign"
			"--no-sign" }
	private boolean sign;

	@Option(name = "--no-sign"
			"--sign" })
	private boolean noSign;

	@Option(name = "--local-user"
			"-u" }
	private String gpgKeyId;
