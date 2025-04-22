			String cryptoName = cryptoAlg.toUpperCase();

				throw new GeneralSecurityException(JGitText.get().encryptionOnlyPBE);

			PBEKeySpec keySpec = new PBEKeySpec(key.toCharArray()
			secretKey = SecretKeyFactory.getInstance(algo).generateSecret(keySpec);


			boolean isJava8 = javaVersion() >= 1.8;

			if (useIV && isJava8) {
				IvParameterSpec paramIV = new IvParameterSpec(ZERO_AES_IV);
				paramSpec = java8PBEParameterSpec(JETS3T_SALT
			} else {
				paramSpec = java7PBEParameterSpec(JETS3T_SALT
			}

			Cipher.getInstance(cryptoAlg);
