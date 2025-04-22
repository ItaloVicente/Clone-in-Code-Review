				(byte) 0xA4
				(byte) 0xD6
		};

		static final byte[] ZERO_AES_IV = new byte[16];

		private final String cryptoVer = JETS3T_VERSION;

		private final String cryptoAlg;

		private final SecretKey secretKey;

		private final AlgorithmParameterSpec paramSpec;

		ObjectEncryptionJets3tV2(final String algo
				throws GeneralSecurityException {
			cryptoAlg = algo;

			String cryptoName = cryptoAlg.toUpperCase();

				throw new GeneralSecurityException(JGitText.get().encryptionOnlyPBE);

			PBEKeySpec keySpec = new PBEKeySpec(key.toCharArray()
			secretKey = SecretKeyFactory.getInstance(algo).generateSecret(keySpec);



			if (useIV && isJava8) {
				IvParameterSpec paramIV = new IvParameterSpec(ZERO_AES_IV);
				paramSpec = new PBEParameterSpec(JETS3T_SALT
			} else {
				paramSpec = new PBEParameterSpec(JETS3T_SALT
			}
