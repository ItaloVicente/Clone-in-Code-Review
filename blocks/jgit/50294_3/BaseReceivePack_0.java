	public void setSignedPushConfig(SignedPushConfig cfg) {
		signedPushConfig = cfg;
	}

	private PushCertificateParser getPushCertificateParser() {
		if (pushCertificateParser == null) {
			pushCertificateParser = new PushCertificateParser(db
		}
		return pushCertificateParser;
	}

