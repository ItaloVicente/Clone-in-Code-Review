			final String cPas = props.getProperty(Keys.PASSWORD);
			if (cPas != null) {
				String cAlg = props.getProperty(Keys.CRYPTO_ALG);
				if (cAlg == null)
					cAlg = WalkEncryption.ObjectEncryptionJetS3tV2.JETS3T_ALGORITHM;
				encryption = new WalkEncryption.ObjectEncryptionJetS3tV2(cAlg, cPas);
			} else {
				encryption = WalkEncryption.NONE;
			}
