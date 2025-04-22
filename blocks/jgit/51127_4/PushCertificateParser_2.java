		enabled = nonceGenerator != null;
	}

	private PushCertificateParser() {
		db = null;
		nonceSlopLimit = 0;
		nonceGenerator = null;
		enabled = true;
