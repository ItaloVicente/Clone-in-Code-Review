			try {
				key = loadKeyFromKeybox(USER_KEYBOX_PATH);
				if (key != null) {
					return key;
				}
				throw new PGPException(MessageFormat.format(
						JGitText.get().gpgNoPublicKeyFound
			} catch (NoOpenPgpKeyException e) {
				if (log.isDebugEnabled()) {
					log.debug("{} does not contain any OpenPGP keys"
							USER_KEYBOX_PATH);
