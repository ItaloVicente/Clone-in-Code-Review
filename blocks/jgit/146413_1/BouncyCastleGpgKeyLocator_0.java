		Exception t = null;
		boolean haveKeybox = exists(USER_KEYBOX_PATH);
		if (haveKeybox) {
			PGPPublicKey publicKey = null;
			try {
				publicKey = findPublicKeyInKeyBox(USER_KEYBOX_PATH);
			} catch (IOException e) {
				t = e;
			}
