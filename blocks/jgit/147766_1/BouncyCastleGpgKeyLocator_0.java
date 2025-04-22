		PGPPublicKey publicKey = null;
		if (hasKeyFiles(USER_SECRET_KEY_DIR)) {
			if (exists(USER_KEYBOX_PATH)) {
				try {
					publicKey = findPublicKeyInKeyBox(USER_KEYBOX_PATH);
					if (publicKey != null) {
						key = findSecretKeyForKeyBoxPublicKey(publicKey
								USER_KEYBOX_PATH);
						if (key != null) {
							return key;
						}
						throw new PGPException(MessageFormat.format(
								JGitText.get().gpgNoSecretKeyForPublicKey
								Long.toHexString(publicKey.getKeyID())));
					}
					throw new PGPException(MessageFormat.format(
							JGitText.get().gpgNoPublicKeyFound
				} catch (NoOpenPgpKeyException e) {
					if (log.isDebugEnabled()) {
						log.debug("{} does not contain any OpenPGP keys"
								USER_KEYBOX_PATH);
					}
				}
			}
			if (exists(USER_PGP_PUBRING_FILE)) {
				publicKey = findPublicKeyInPubring(USER_PGP_PUBRING_FILE);
				if (publicKey != null) {
					key = findSecretKeyForKeyBoxPublicKey(publicKey
							USER_PGP_PUBRING_FILE);
					if (key != null) {
						return key;
					}
