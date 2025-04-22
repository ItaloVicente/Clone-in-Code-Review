		static boolean isAlgorithmPresent(String algorithm) {
			Set<String> cipherSet = Security.getAlgorithms("Cipher");
			for (String source : cipherSet) {
				String target = algorithm.toUpperCase();
				if (source.equalsIgnoreCase(target)) {
					return true;
				}
			}
			return false;
		}

		static boolean isAlgorithmPresent(Properties props) {
			String profile = props.getProperty(AmazonS3.Keys.CRYPTO_ALG);
			String version = props.getProperty(AmazonS3.Keys.CRYPTO_VER
					WalkEncryption.Vals.DEFAULT_VERS);
			String crytoAlgo;
			String keyAlgo;
			switch (version) {
			case WalkEncryption.Vals.DEFAULT_VERS:
			case WalkEncryption.JGitV1.VERSION:
				crytoAlgo = profile;
				keyAlgo = profile;
				break;
			case WalkEncryption.JGitV2.VERSION:
				crytoAlgo = props
						.getProperty(profile + WalkEncryption.Keys.X_ALGO);
				keyAlgo = props
						.getProperty(profile + WalkEncryption.Keys.X_KEY_ALGO);
				break;
			default:
				return false;
			}
			try {
				Cipher.getInstance(crytoAlgo);
				SecretKeyFactory.getInstance(keyAlgo);
				return true;
			} catch (Throwable e) {
				return false;
			}
		}

