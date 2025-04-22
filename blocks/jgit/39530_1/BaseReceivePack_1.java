		certNonceSeed = cfg.certNonceSeed;
		if (!certNonceSeed.isEmpty())
			pushCertNonce = preparePushCertNonce(
					into.getDirectory().getPath()
					new Date(0).getTime() / milliSecondsPerSecond);
		certNonceSlopLimit = cfg.certNonceSlopLimit;
	}

	protected String preparePushCertNonce(String path
		throws IllegalStateException
	{

		byte[] keyBytes = key.getBytes();
		SecretKeySpec signingKey = new SecretKeySpec(keyBytes

		Mac mac = null;
		try {
			mac.init(signingKey);
		} catch (InvalidKeyException e) {
			throw new IllegalStateException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
		byte[] rawHmac = mac.doFinal(certNonceSeed.getBytes());
		String hexString = String.format("%20X"
		return hexString;
